package cl.tesis.tls;


import cl.tesis.output.JsonWritable;
import cl.tesis.tls.handshake.TLSCipher;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ScanCiphersSuites implements JsonWritable{
    private String null_ciphers;
    private String anonymous_null_ciphers;
    private String anonymous_dh_ciphers;
    private String export_40_ciphers;
    private String low_ciphers;
    private String medium_ciphers;
    private String des3_ciphers;
    private String high_ciphers;

    public ScanCiphersSuites() {
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

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
