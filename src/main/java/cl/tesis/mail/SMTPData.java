package cl.tesis.mail;

import cl.tesis.output.CSVWritable;
import cl.tesis.output.JsonWritable;
import cl.tesis.tls.Certificate;
import cl.tesis.tls.HeartbleedData;
import cl.tesis.tls.ScanCipherSuitesData;
import cl.tesis.tls.ScanTLSProtocolsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class SMTPData implements CSVWritable, JsonWritable{

    private String ip;
    private String error;
    private String banner;
    private String help;
    private String ehlo;
    private Certificate[] chain;
    private HeartbleedData heartbleedData;
    private String beastCipher;
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

    public void setBanner(String banner) {
        this.banner = banner;
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

    public void setHeartbleedData(HeartbleedData heartbleedData) {
        this.heartbleedData = heartbleedData;
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
        parameters.add("banner");
        parameters.add("help");
        parameters.add("ehlo");

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values =  new ArrayList<>();

        values.add(this.ip);
        values.add(this.banner);
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
