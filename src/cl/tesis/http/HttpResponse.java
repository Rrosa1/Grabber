package cl.tesis.http;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpResponse implements CSVWritable, JsonWritable{

    private String ip;
    private String responseCode;
    private String server;
//    private String contentType;
    private String domain;
//    private List<String> cookies;

    private Map<String, List<String>> header;
    private String index;

    public HttpResponse(String ip, Map<String, List<String>> header, String index) {
        this.ip = ip;
        this.responseCode =  getKey(header, null);
        this.server = getKey(header, "Server");
//        this.contentType =  getKey(header, "Content-Type");
//        this.cookies = header.get("Set-Cookie");
        this.domain = this.getDomain(header.get("Set-Cookie"));
        // new elements
        this.header = header;
        this.index = index;
    }

    private static String getKey(Map<String, List<String>> header, String key){
        List<String> values = header.get(key);

        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    private String getDomain(List<String> cookies) {
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

//    private String getHeader(Map<String, List<String>> header) {
//        StringBuffer response = new StringBuffer();
//        for (Map.Entry<String, List<String>> entry : header.entrySet()) {
//            response.append(entry.getKey());
//            response.append(entry.getValue());
//        }
//
//    }

    @Override
    public String toString() {
        return "HttpResponse{" +
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
//        parameters.add("content type");
        parameters.add("domain");
//        parameters.add("cookies");
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
//        values.add(this.contentType);
        values.add(this.domain);
//        values.add(this.cookies + "");
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
