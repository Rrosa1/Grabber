package cl.tesis.https;

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

public class HttpsCertificateData implements CSVWritable, JsonWritable{
    private String ip;
    private String error;
    private String tlsProtocol;
    private String cipherSuite;
    private Certificate[] chain;
    private HeartbleedData heartbleed;
    private ScanTLSProtocolsData protocols;
    private ScanCipherSuitesData ciphersSuites;

    public HttpsCertificateData(String ip) {
        this.ip = ip;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setTLSProtocol(String tlsProtocol) {
        this.tlsProtocol = tlsProtocol;
    }

    public void setCipherSuite(String cipherSuite) {
        this.cipherSuite = cipherSuite;
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

    // TODO remove csvfilewriter
    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters = new ArrayList<>();

        parameters.add("ip");
        parameters.add("error");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.error);

        return values;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
