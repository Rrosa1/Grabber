package cl.tesis.http;

import java.util.List;
import java.util.Map;

public class HttpHeader {

    private String ip;
    private String response;
    private String server;
    private String contentType;
    private String domain;
    private List<String> cookie;

    public HttpHeader(String ip, Map<String, List<String>> header) {
        this.ip = ip;
        this.response =  getKey(header, null);
        this.server = getKey(header, "Server");
        this.contentType =  getKey(header, "Content-Type");
        this.cookie = header.get("Set-Cookie");
        this.domain = this.getDomain();
    }

    private static String getKey(Map<String, List<String>> header, String key){
        List<String> values = header.get(key);

        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    private String getDomain() {
        if (this.cookie == null)
            return null;

        for(String s : this.cookie) {
            if (s.contains("domain")) {
                int start = s.indexOf("domain=") + 7; // 7 for "domain=" string length
                return s.substring(start);
            }
        }
        return null;

    }

    @Override
    public String toString() {
        return "HttpHeader{" +
                "ip='" + ip + '\'' +
                ", response='" + response + '\'' +
                ", server='" + server + '\'' +
                ", contentType='" + contentType + '\'' +
                ", domain='" + domain + '\'' +
                ", cookie=" + cookie +
                '}';
    }

    // TODO implement json and csv methods
    public String toJson() {
        return null;
    }
}
