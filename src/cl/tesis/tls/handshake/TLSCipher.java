package cl.tesis.tls.handshake;


import com.sun.xml.internal.ws.api.model.wsdl.WSDLBoundOperation;

import static cl.tesis.tls.handshake.CipherSuites.*;

public enum TLSCipher {
    NULL_CIPHERS(TLS_ECDHE_RSA_WITH_NULL_SHA.getStringValue() + TLS_ECDHE_ECDSA_WITH_NULL_SHA.getStringValue() +
            TLS_ECDH_ANON_WITH_NULL_SHA.getStringValue() + TLS_ECDH_RSA_WITH_NULL_SHA.getStringValue() +
            TLS_ECDH_ECDSA_WITH_NULL_SHA.getStringValue() + TLS_RSA_WITH_NULL_SHA256.getStringValue() +
            TLS_RSA_WITH_NULL_SHA.getStringValue() + TLS_RSA_WITH_NULL_MD5.getStringValue()),
    ANONYMOUS_NULL_CIPHERS(TLS_ECDH_ANON_WITH_AES_256_CBC_SHA.getStringValue() + TLS_DH_ANON_WITH_AES_256_GCM_SHA384.getStringValue() +
            TLS_DH_ANON_WITH_AES_256_CBC_SHA256.getStringValue() + TLS_DH_ANON_WITH_AES_256_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_CAMELLIA_256_CBC_SHA.getStringValue() + TLS_ECDH_ANON_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_ECDH_ANON_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_AES_128_GCM_SHA256.getStringValue() + TLS_DH_ANON_WITH_AES_128_CBC_SHA256.getStringValue() +
            TLS_DH_ANON_WITH_AES_128_CBC_SHA.getStringValue() + TLS_DH_ANON_WITH_SEED_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_CAMELLIA_128_CBC_SHA.getStringValue() + TLS_ECDH_ANON_WITH_RC4_128_SHA.getStringValue() +
            TLS_DH_ANON_WITH_RC4_128_MD5.getStringValue() + TLS_DH_ANON_WITH_DES_CBC_SHA.getStringValue() +
            TLS_DH_ANON_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + TLS_DH_ANON_EXPORT_WITH_RC4_40_MD5.getStringValue() +
            TLS_ECDH_ANON_WITH_NULL_SHA.getStringValue()),
    ANONYMOUS_DH_CIPHERS(TLS_DH_ANON_WITH_AES_256_GCM_SHA384.getStringValue() + TLS_DH_ANON_WITH_AES_256_CBC_SHA256.getStringValue() +
            TLS_DH_ANON_WITH_AES_256_CBC_SHA.getStringValue() + TLS_DH_ANON_WITH_CAMELLIA_256_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_DH_ANON_WITH_AES_128_GCM_SHA256.getStringValue() +
            TLS_DH_ANON_WITH_AES_128_CBC_SHA256.getStringValue() + TLS_DH_ANON_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_SEED_CBC_SHA.getStringValue() + TLS_DH_ANON_WITH_CAMELLIA_128_CBC_SHA.getStringValue() +
            TLS_DH_ANON_WITH_RC4_128_MD5.getStringValue() + TLS_DH_ANON_WITH_DES_CBC_SHA.getStringValue() +
            TLS_DH_ANON_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + TLS_DH_ANON_EXPORT_WITH_RC4_40_MD5.getStringValue()),
    EXPORT_40_CIPHERS(TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA.getStringValue() +
            TLS_DH_ANON_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + TLS_RSA_EXPORT_WITH_DES40_CBC_SHA.getStringValue() +
            TLS_RSA_EXPORT_WITH_RC2_CBC_40_MD5.getStringValue() + TLS_DH_ANON_EXPORT_WITH_RC4_40_MD5.getStringValue() +
            TLS_RSA_EXPORT_WITH_RC4_40_MD5.getStringValue()),
    LOW_CIPHERS(TLS_DHE_RSA_WITH_DES_CBC_SHA.getStringValue() + TLS_DHE_DSS_WITH_DES_CBC_SHA.getStringValue() +
            TLS_RSA_WITH_DES_CBC_SHA.getStringValue()),
    MEDIUM_CIPHERS(TLS_DHE_RSA_WITH_SEED_CBC_SHA.getStringValue() + TLS_DHE_DSS_WITH_SEED_CBC_SHA.getStringValue() +
            TLS_RSA_WITH_SEED_CBC_SHA.getStringValue() + TLS_ECDHE_RSA_WITH_RC4_128_SHA.getStringValue() +
            TLS_ECDHE_ECDSA_WITH_RC4_128_SHA.getStringValue() + TLS_ECDH_RSA_WITH_RC4_128_SHA.getStringValue() +
            TLS_RSA_WITH_RC4_128_SHA.getStringValue() + TLS_RSA_WITH_RC4_128_MD5.getStringValue() +
            TLS_PSK_WITH_RC4_128_SHA.getStringValue()),
    DES3_CIPHERS(TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            TLS_SRP_SHA_DSS_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_SRP_SHA_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            TLS_SRP_SHA_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            TLS_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() + TLS_PSK_WITH_3DES_EDE_CBC_SHA.getStringValue()),
    HIGH_CIPHERS(TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384.getStringValue() + TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384.getStringValue() +
            TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384.getStringValue() + TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384.getStringValue() +
            TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA.getStringValue() + TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA.getStringValue() +
            TLS_SRP_SHA_DSS_WITH_AES_256_CBC_SHA.getStringValue() + TLS_SRP_SHA_RSA_WITH_AES_256_CBC_SHA.getStringValue() +
            TLS_SRP_SHA_WITH_AES_256_CBC_SHA.getStringValue() + TLS_DHE_DSS_WITH_AES_256_GCM_SHA384.getStringValue() +
            TLS_DHE_RSA_WITH_AES_256_GCM_SHA384.getStringValue() + TLS_DHE_RSA_WITH_AES_256_CBC_SHA256.getStringValue() +
            TLS_DHE_DSS_WITH_AES_256_CBC_SHA256.getStringValue() + TLS_DHE_RSA_WITH_AES_256_CBC_SHA.getStringValue() +
            TLS_DHE_DSS_WITH_AES_256_CBC_SHA.getStringValue() + TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA.getStringValue() +
            TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA.getStringValue() + TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384.getStringValue() +
            TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384.getStringValue() + TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384.getStringValue() +
            TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384.getStringValue() + TLS_ECDH_RSA_WITH_AES_256_CBC_SHA.getStringValue() +
            TLS_RSA_WITH_AES_256_GCM_SHA384.getStringValue() + TLS_RSA_WITH_AES_256_CBC_SHA256.getStringValue() +
            TLS_RSA_WITH_AES_256_CBC_SHA.getStringValue() + TLS_RSA_WITH_CAMELLIA_256_CBC_SHA.getStringValue() +
            TLS_PSK_WITH_AES_256_CBC_SHA.getStringValue() + TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256.getStringValue() + TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256.getStringValue() +
            TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256.getStringValue() + TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA.getStringValue() + TLS_SRP_SHA_DSS_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA.getStringValue() + TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_DHE_DSS_WITH_AES_128_GCM_SHA256.getStringValue() + TLS_DHE_RSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            TLS_DHE_RSA_WITH_AES_128_CBC_SHA256.getStringValue() + TLS_DHE_DSS_WITH_AES_128_CBC_SHA256.getStringValue() +
            TLS_DHE_RSA_WITH_AES_128_CBC_SHA.getStringValue() + TLS_DHE_DSS_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA.getStringValue() + TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA.getStringValue() +
            TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256.getStringValue() + TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256.getStringValue() + TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256.getStringValue() +
            TLS_ECDH_RSA_WITH_AES_128_CBC_SHA.getStringValue() + TLS_RSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            TLS_RSA_WITH_AES_128_CBC_SHA256.getStringValue() + TLS_RSA_WITH_AES_128_CBC_SHA.getStringValue() +
            TLS_RSA_WITH_CAMELLIA_128_CBC_SHA.getStringValue() + TLS_PSK_WITH_AES_128_CBC_SHA.getStringValue());


    private String value;

    TLSCipher(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
