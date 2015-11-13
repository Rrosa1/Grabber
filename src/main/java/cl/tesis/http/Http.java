package cl.tesis.http;

import cl.tesis.http.exception.HTTPConnectionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.security.PrivateKey;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Http {
    private static final Logger logger = Logger.getLogger(Http.class.getName());

    private static final String HTTP = "http";
    private static final String GET = "GET";

    private static final int TIMEOUT = 60000;
    private static final int DEFAULT_PORT = 80;
    private static final int INDEX_SIZE = 4 * 1024; // 6 Kb

    private URL url;
    private HttpURLConnection connection;

    public Http (String host) throws HTTPConnectionException {
        this(host, DEFAULT_PORT, "");
    }

    public Http(String host, int port) throws HTTPConnectionException {
        this(host, port, "");
    }

    public Http(String host,int port, String file) throws HTTPConnectionException {
        try {
            this.url = new URL(HTTP, host, port, file);
            this.connection = (HttpURLConnection) url.openConnection();

            // Setting methods
            this.connection.setRequestMethod(GET);
            this.connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Setting timeouts
            this.connection.setConnectTimeout(TIMEOUT);
            this.connection.setReadTimeout(TIMEOUT);
        } catch (IOException e) {
            throw new HTTPConnectionException();
        }

    }

    public Map<String, List<String>> getHeader() {
        return this.connection.getHeaderFields();
    }

    public String getIndex() {
        char[] index = new char[INDEX_SIZE];
        int readChars;

        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
            readChars = in.read(index);
            in.close();
        } catch (IOException e) {
            logger.log(Level.INFO, "Error getting index {0}", this.url.getHost());
            return null;
        }

        return new String(index, 0, readChars);
    }

    public void close() {
        this.connection.disconnect();
    }
}
