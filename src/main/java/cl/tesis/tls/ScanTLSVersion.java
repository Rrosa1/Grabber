package cl.tesis.tls;


import cl.tesis.output.JsonWritable;
import cl.tesis.tls.handshake.TLSVersion;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ScanTLSVersion implements JsonWritable{
    private boolean SSL_30;
    private boolean TLS_10;
    private boolean TLS_11;
    private boolean TLS_12;

    public ScanTLSVersion() {
        super();
    }

    public void setTLSVersion(TLSVersion tls, boolean support) {
        switch (tls.getName()) {
            case "SSL 3.0":
                SSL_30 = support;
                break;
            case "TLS 1.0":
                TLS_10 = support;
                break;
            case "TLS 1.1":
                TLS_11 = support;
                break;
            case "TLS 1.2":
                TLS_12 = support;
                break;
        }
    }

    public boolean isSSL_30() {
        return SSL_30;
    }

    public boolean isTLS_10() {
        return TLS_10;
    }

    public boolean isTLS_11() {
        return TLS_11;
    }

    public boolean isTLS_12() {
        return TLS_12;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
