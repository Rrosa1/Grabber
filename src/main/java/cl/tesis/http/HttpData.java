package cl.tesis.http;

import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;
import java.util.Map;

public class HttpData implements JsonWritable{

    private String ip;
    private String error;
    private Map<String, List<String>> header;
    private String index;

    public void clear() {
        this.ip = null;
        this.error = null;
        this.header = null;
        this.index = null;
    }

    private static String getKey(Map<String, List<String>> header, String key){
        List<String> values = header.get(key);

        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    private String parseDomain(List<String> cookies) {
        if (cookies == null)
            return null;

        for(String s : cookies) {
            if (s.contains("domain")) {
                int start = s.indexOf("domain=") + 7; // 7 for "domain=" string length
                return s.substring(start);
            }
        }
        return null;
    }

   /* GETTER AND SETTER */

    public String getIp() {
        return ip;
    }

    public String getError() {
        return error;
    }

    public Map<String, List<String>> getHeader() {
        return header;
    }

    public String getIndex() {
        return index;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setHeader(Map<String, List<String>> header) {
        this.header = header;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    /* String Methods */

    @Override
    public String toString() {
        return "HttpData{" +
                "ip='" + ip + '\'' +
                ", header=" + header +
                ", index='" + index + '\'' +
                '}';
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
