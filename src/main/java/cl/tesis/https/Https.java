package cl.tesis.https;


import cl.tesis.http.exception.HTTPHeaderException;
import cl.tesis.http.exception.HTTPIndexException;
import cl.tesis.https.exception.HTTPSHeaderException;
import cl.tesis.https.exception.HTTPSIndexException;

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
    private static final int INDEX_SIZE = 8 * 1024; // 8 Kb

    private URL url;
    private HttpsURLConnection connection;
    private BufferedReader in;

    public Https(String ip, int port) throws NoSuchAlgorithmException, KeyManagementException, IOException {
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("TLS");
        sc.init(null, TrustAllCert.getManager(), new java.security.SecureRandom());
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

        // Create all-trusting host name verifier
        HostnameVerifier allHostsValid = TrustAllCert.getHostnameVerifier();

        // Install the all-trusting host verifier
        HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);

        this.url = new URL(HTTPS, ip, port, "");
        this.connection = (HttpsURLConnection) url.openConnection();

        // Setting timeouts
        this.connection.setConnectTimeout(TIMEOUT);
        this.connection.setReadTimeout(TIMEOUT);

        // Setting methods
        this.connection.setInstanceFollowRedirects(false);
        this.connection.setRequestMethod(GET);
        this.connection.setRequestProperty("User-Agent", "Mozilla/5.0");

        // Setting Buffer Reader
        this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

    }

    public Map<String, List<String>> getHeader() throws HTTPSHeaderException {
        Map<String, List<String>> header;

        try {
            Thread.sleep(10000);
            header = this.connection.getHeaderFields();
        } catch (InterruptedException e) {
            throw new HTTPSHeaderException();
        }

        if (header != null && header.size() == 0) {
            throw new HTTPSHeaderException();
        }

        return header;
    }

    public String getIndex() throws HTTPSIndexException {
        char[] index = new char[INDEX_SIZE];
        int readChars;

        try {
            readChars = in.read(index);
            in.close();
        } catch (IOException e) {
            throw new HTTPSIndexException();
        }

        if (readChars <= 0)
            return null;
        else
            return new String(index, 0, readChars);
    }
}
