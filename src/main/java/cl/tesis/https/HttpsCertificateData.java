package cl.tesis.https;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import cl.tesis.tls.Heartbleed;
import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.ScanCiphersSuites;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import tlsNew.ScanTLSProtocolsData;

import java.util.ArrayList;
import java.util.List;

public class HttpsCertificateData implements CSVWritable, JsonWritable{
    private String ip;
    private String error;
    private String tlsProtocol;
    private String cipherSuite;
    private HostCertificate certificate; // Todo change to array of certs
    private Heartbleed heartbleed;
    private ScanTLSProtocolsData protocols;
    private ScanCiphersSuites ciphersSuites;

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

    public void setCertificate(HostCertificate certificate) {
        this.certificate = certificate;
    }

    public void setHeartbleed(Heartbleed heartbleed) {
        this.heartbleed = heartbleed;
    }

    public void setProtocols(ScanTLSProtocolsData protocols) {
        this.protocols = protocols;
    }

    public void setCiphersSuites(ScanCiphersSuites ciphersSuites) {
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
