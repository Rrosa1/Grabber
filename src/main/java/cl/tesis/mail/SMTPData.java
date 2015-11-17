package cl.tesis.mail;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tlsNew.Certificate;
import tlsNew.HeartbleedData;
import tlsNew.ScanCipherSuitesData;
import tlsNew.ScanTLSProtocolsData;

import java.util.ArrayList;
import java.util.List;

public class SMTPData implements CSVWritable, JsonWritable{

    private String ip;
    private String error;
    private String start;
    private String help;
    private String ehlo;
    private Certificate[] chain;
    private HeartbleedData heartbleed;
    private ScanTLSProtocolsData protocols;
    private ScanCipherSuitesData ciphersSuites;

    public SMTPData(String ip) {
        this.ip = ip;
    }

    public boolean supportTLS() {
        return this.ehlo.contains("STARTTLS");
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setHelp(String help) {
        this.help = help;
    }

    public void setEhlo(String ehlo) {
        this.ehlo = ehlo;
    }

    public void setChain(Certificate[] certificates) {
        this.chain = certificates;
    }

    public void setHeartbleed(HeartbleedData heartbleed) {
        this.heartbleed = heartbleed;
    }

    public void setProtocols(ScanTLSProtocolsData protocols) {
        this.protocols = protocols;
    }

    public void setCiphersSuites(ScanCipherSuitesData ciphersSuites) {
        this.ciphersSuites = ciphersSuites;
    }

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("start");
        parameters.add("help");
        parameters.add("ehlo");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.start);
        values.add(this.help);
        values.add(this.ehlo);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
