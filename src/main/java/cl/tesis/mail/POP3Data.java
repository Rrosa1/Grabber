package cl.tesis.mail;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import cl.tesis.tls.HostCertificate;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tlsNew.HeartbleedData;
import tlsNew.ScanCipherSuitesData;
import tlsNew.ScanTLSProtocolsData;

import java.util.ArrayList;
import java.util.List;

public class POP3Data implements JsonWritable, CSVWritable{

    private String ip;
    private String error;
    private String start;
    private HostCertificate certificate;
    private HeartbleedData heartbleed;
    private ScanTLSProtocolsData protocols;
    private ScanCipherSuitesData ciphersSuites;

    public POP3Data(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public void setCertificate(HostCertificate certificate) {
        this.certificate = certificate;
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

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.start);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
