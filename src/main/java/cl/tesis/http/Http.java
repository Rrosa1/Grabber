package cl.tesis.http;

import cl.tesis.http.exception.HTTPConnectionException;
import cl.tesis.http.exception.HTTPHeaderException;
import cl.tesis.http.exception.HTTPIndexException;

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
    private static final int INDEX_SIZE = 8 * 1024; // 8 Kb

    private URL url;
    private HttpURLConnection connection;
    private BufferedReader in;

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

            // Setting timeouts
            this.connection.setConnectTimeout(TIMEOUT);
            this.connection.setReadTimeout(TIMEOUT);

            // Setting methods
            this.connection.setInstanceFollowRedirects(false);
            this.connection.setRequestMethod(GET);
            this.connection.setRequestProperty("User-Agent", "Mozilla/5.0");

            // Setting the Buffer Reader
            this.in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));

        } catch (IOException e) {
            throw new HTTPConnectionException();
        }

    }

    public Map<String, List<String>> getHeader() throws HTTPHeaderException {
        Map<String, List<String>> header;

        try {
            Thread.sleep(10000);
            header = this.connection.getHeaderFields();
        } catch (InterruptedException e) {
            throw new HTTPHeaderException();
        }

        if (header != null && header.size() == 0) {
            throw new HTTPHeaderException();
        }

        return header;
    }

    public String getIndex() throws HTTPIndexException {
        char[] index = new char[INDEX_SIZE];
        int readChars;

        try {
            readChars = in.read(index);
            in.close();
        } catch (IOException e) {
            throw new HTTPIndexException();
        }

        if (readChars <= 0)
            return null;
        else
            return new String(index, 0, readChars);
    }
}
