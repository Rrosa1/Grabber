package cl.tesis.https;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Https {
    private static final Logger logger = Logger.getLogger(Https.class.getName());

    private static final String HTTPS = "https";
    private static final String GET = "GET";

    private static final int TIMEOUT = 60000;
    private static final int INDEX_SIZE = 4 * 1024; // 4 Kb

    private URL url;
    private HttpsURLConnection connection;

    public Https(String ip, int port) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, TrustAllCert.getManager(), new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = TrustAllCert.getHostnameVerifier();

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        this.url = new URL(HTTPS, ip, port, "");
        this.connection = (HttpsURLConnection) url.openConnection();

        // Setting methods
        this.connection.setRequestMethod(GET);
        this.connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Setting timeouts
        this.connection.setConnectTimeout(TIMEOUT);
        this.connection.setReadTimeout(TIMEOUT);
    }

    public Map<String, List<String>> getHeader() {
        return this.connection.getHeaderFields();
    }

    public String getIndex() {
        char[] index = new char[INDEX_SIZE];
        int readChars = 0;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            readChars = in.read(index);
            in.close();
        } catch (IOException e) {
            logger.log(Level.INFO, "Error getting index {0}", this.url.getHost());
            return null;
        }

        if (readChars <= 0)
            return null;
        else
            return new String(index, 0, readChars);
    }

    public void close() {
        this.connection.disconnect();
    }
}
