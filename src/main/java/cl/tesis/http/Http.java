package cl.tesis.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Http {
    private static final Logger logger = Logger.getLogger(Http.class.getName());

    private final static String HTTP = "http";
    private final static String GET = "GET";
    private final static int TIMEOUT = 60000;

    private URL url;
    private HttpURLConnection connection;

    public Http(String host) throws IOException {
        this(host, "");
    }

    public Http(String host, String file) throws IOException {
        this.url = new URL(HTTP, host, file);
        this.connection =  (HttpURLConnection) url.openConnection();

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
        StringBuilder response = new StringBuilder();

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
        } catch (IOException e) {
            logger.log(Level.INFO, "Error getting index {0}", this.url.getHost());
            return null;
        }

        return response.toString();
    }
}
