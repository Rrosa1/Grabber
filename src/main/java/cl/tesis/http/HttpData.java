package cl.tesis.http;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpData implements CSVWritable, JsonWritable{

    private String ip;
    private String error;
    private String responseCode;
    private String server;
    private String domain;

    private Map<String, List<String>> header;
    private String index;

    public void clear() {
        this.ip = null;
        this.error = null;
        this.responseCode =  null;
        this.server = null;
        this.domain = null;
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

    public String getResponseCode() {
        return responseCode;
    }

    public String getServer() {
        return server;
    }

    public String getDomain() {
        return domain;
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
        this.responseCode =  getKey(header, null);
        this.server = getKey(header, "Server");
        this.domain = this.parseDomain(header.get("Set-Cookie"));
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
                ", responseCode='" + responseCode + '\'' +
                ", server='" + server + '\'' +
                ", domain='" + domain + '\'' +
                ", header=" + header +
                ", index='" + index + '\'' +
                '}';
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("response code");
        parameters.add("server");
        parameters.add("domain");
        parameters.add("header");
        parameters.add("index");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.responseCode);
        values.add(this.server);
        values.add(this.domain);
        values.add(new GsonBuilder().disableHtmlEscaping().create().toJson(this.header));
        values.add(this.index);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
