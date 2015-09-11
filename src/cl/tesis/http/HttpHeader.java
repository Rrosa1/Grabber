package cl.tesis.http;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpHeader implements CSVWritable, JsonWritable{

    private String ip;
    private String response;
    private String server;
    private String contentType;
    private String domain;
    private List<String> cookies;

    public HttpHeader(String ip, Map<String, List<String>> header) {
        this.ip = ip;
        this.response =  getKey(header, null);
        this.server = getKey(header, "Server");
        this.contentType =  getKey(header, "Content-Type");
        this.cookies = header.get("Set-Cookie");
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
        if (this.cookies == null)
            return null;

        for(String s : this.cookies) {
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
                ", cookies=" + cookies +
                '}';
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("response");
        parameters.add("server");
        parameters.add("content type");
        parameters.add("domain");
        parameters.add("cookies");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.response);
        values.add(this.server);
        values.add(this.contentType);
        values.add(this.domain);
        values.add(this.cookies + "");

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
