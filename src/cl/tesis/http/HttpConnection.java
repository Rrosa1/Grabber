package cl.tesis.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.List;
import java.util.Map;

public class HttpConnection {

    private final static String HTTP = "http";
    private final static String GET = "GET";
    private final static int TIMEOUT = 60000;

    private URL url;
    private HttpURLConnection connection;

    public HttpConnection(String host) throws IOException {
        this(host, "");
    }

    public HttpConnection(String host, String file) throws IOException {
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

    public String getIndex() throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(this.connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        return response.toString();
    }

    public static void main(String[] args) throws IOException {
        HttpConnection con = new HttpConnection("www.google.cl");
        HttpResponse response =  new HttpResponse("www.google.cl", con.getHeader(), con.getIndex());
        System.out.println(response.toJson());
    }
}
