package cl.tesis.ssh;

import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SSHData implements JsonWritable{

    private String ip;
    private String error;
    private String response;

    public SSHData(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "SSHData{" +
                "ip='" + ip + '\'' +
                ", response='" + response + '\'' +
                '}';
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
