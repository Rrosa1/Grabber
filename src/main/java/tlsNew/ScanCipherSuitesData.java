package tlsNew;


import cl.tesis.output.JsonWritable;
import cl.tesis.tls.handshake.TLSCipher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ScanCipherSuitesData implements JsonWritable{
    private String null_ciphers;
    private String anonymous_null_ciphers;
    private String anonymous_dh_ciphers;
    private String export_40_ciphers;
    private String low_ciphers;
    private String medium_ciphers;
    private String des3_ciphers;
    private String high_ciphers;

    public ScanCipherSuitesData() {
        super();
    }

    public void setCipherSuite(TLSCipher cipherType, String cipher) {
        switch (cipherType) {
            case NULL_CIPHERS:
                null_ciphers = cipher;
                break;
            case ANONYMOUS_NULL_CIPHERS:
                anonymous_null_ciphers = cipher;
                break;
            case ANONYMOUS_DH_CIPHERS:
                anonymous_dh_ciphers = cipher;
                break;
            case EXPORT_40_CIPHERS:
                export_40_ciphers = cipher;
                break;
            case LOW_CIPHERS:
                low_ciphers = cipher;
                break;
            case MEDIUM_CIPHERS:
                medium_ciphers = cipher;
                break;
            case DES3_CIPHERS:
                des3_ciphers = cipher;
                break;
            case HIGH_CIPHERS:
                high_ciphers = cipher;
                break;
        }
    }

    public String getNull_ciphers() {
        return null_ciphers;
    }

    public String getAnonymous_null_ciphers() {
        return anonymous_null_ciphers;
    }

    public String getAnonymous_dh_ciphers() {
        return anonymous_dh_ciphers;
    }

    public String getExport_40_ciphers() {
        return export_40_ciphers;
    }

    public String getLow_ciphers() {
        return low_ciphers;
    }

    public String getMedium_ciphers() {
        return medium_ciphers;
    }

    public String getDes3_ciphers() {
        return des3_ciphers;
    }

    public String getHigh_ciphers() {
        return high_ciphers;
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
