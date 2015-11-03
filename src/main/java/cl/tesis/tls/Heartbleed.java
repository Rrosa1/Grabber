package cl.tesis.tls;

import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class Heartbleed implements JsonWritable {
    private boolean heartbeat;
//    private boolean heartbleed;

    public Heartbleed(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }

    public boolean isHeartbeat() {
        return heartbeat;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
