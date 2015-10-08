package cl.tesis.tls;


import cl.tesis.output.JsonWritable;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import static cl.tesis.tls.TLSCipher.*;

public class ScanCiphersSuites implements JsonWritable{
    private boolean null_ciphers;
    private boolean anonymous_null_ciphers;
    private boolean anonymous_dh_ciphers;
    private boolean export_40_ciphers;
    private boolean low_ciphers;
    private boolean medium_ciphers;
    private boolean des3_ciphers;
    private boolean high_ciphers;

    public ScanCiphersSuites() {
        super();
    }

    public void setCipherSuite(TLSCipher cipher, boolean support) {
        switch (cipher) {
            case NULL_CIPHERS:
                null_ciphers = support;
                break;
            case ANONYMOUS_NULL_CIPHERS:
                anonymous_null_ciphers = support;
                break;
            case ANONYMOUS_DH_CIPHERS:
                anonymous_dh_ciphers = support;
                break;
            case EXPORT_40_CIPHERS:
                export_40_ciphers = support;
                break;
            case LOW_CIPHERS:
                low_ciphers = support;
                break;
            case MEDIUM_CIPHERS:
                medium_ciphers = support;
                break;
            case DES3_CIPHERS:
                des3_ciphers = support;
                break;
            case HIGH_CIPHERS:
                high_ciphers = support;
                break;
        }
    }

    @Override
    public String toJson() {
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();
        return gson.toJson(this);
    }
}
