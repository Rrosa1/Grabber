package cl.tesis.http;

import javax.net.SocketFactory;
import java.io.IOException;
import java.net.*;
import java.util.List;
import java.util.Map;

public class HttpConnection {

    private final static String HTTP = "http";
    private final static int TIMEOUT = 60000;

    private URL url;
    private URLConnection connection;

    public HttpConnection(String host) throws IOException {
        this(host, "");
    }

    public HttpConnection(String host, String file) throws IOException {
        this.url = new URL(HTTP, host, file);
        this.connection =  url.openConnection();

        // Setting timeouts
        this.connection.setConnectTimeout(TIMEOUT);
        this.connection.setReadTimeout(TIMEOUT);
    }

    public HttpHeader getHeader() {
        Map<String, List<String>> header = this.connection.getHeaderFields();
        return new HttpHeader(this.url.getHost(), header);
    }
}
