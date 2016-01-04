package cl.tesis.database;


import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class MySQLData implements CSVWritable, JsonWritable {
    private String ip;
    private String error;
    private String response;

    public MySQLData(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("response");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values = new ArrayList<>();

        values.add(this.ip);
        values.add(this.response);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
