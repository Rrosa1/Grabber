package cl.tesis.mail;

import cl.tesis.output.JsonWritable;
import cl.tesis.tls.Certificate;
import cl.tesis.tls.HeartbleedData;
import cl.tesis.tls.ScanCipherSuitesData;
import cl.tesis.tls.ScanTLSProtocolsData;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class SMTPData implements JsonWritable{

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

    public void setBeastCipher(String beastCipher) {
        this.beastCipher = beastCipher;
    }

    public void setProtocols(ScanTLSProtocolsData protocols) {
        this.protocols = protocols;
    }

    public void setCiphersSuites(ScanCipherSuitesData ciphersSuites) {
        this.ciphersSuites = ciphersSuites;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
