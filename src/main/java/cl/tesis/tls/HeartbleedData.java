package cl.tesis.tls;

import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class HeartbleedData implements JsonWritable {
    private boolean heartbeat;
    private boolean heartbleed;

    public HeartbleedData() {}

    public HeartbleedData(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }

    public void setHeartbeat(boolean heartbeat) {
        this.heartbeat = heartbeat;
    }

    public void setHeartbleed(boolean heartbleed) {
        this.heartbleed = heartbleed;
    }

    public boolean isHeartbeat() {
        return heartbeat;
    }

    public boolean isHeartbleed() {
        return heartbleed;
    }

    @Override
    public String toString() {
        return "HeartbleedData{" +
                "heartbeat=" + heartbeat +
                ", heartbleed=" + heartbleed +
                '}';
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
