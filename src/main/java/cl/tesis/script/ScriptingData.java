package cl.tesis.script;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

public class ScriptingData implements CSVWritable, JsonWritable{
    @Override
    public List<String> getParameterList() {
        return null;
    }

    @Override
    public List<String> getValueList() {
        return null;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
