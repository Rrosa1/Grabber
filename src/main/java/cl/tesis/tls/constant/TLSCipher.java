package cl.tesis.tls.constant;

public enum TLSCipher {
    TEST(CipherSuites.TLS_DHE_RSA_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDHE_RSA_WITH_RC4_128_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_NULL_MD5.getStringValue() + CipherSuites.TLS_RSA_WITH_NULL_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_NULL_SHA256.getStringValue() + CipherSuites.TLS_RSA_WITH_RC4_128_MD5.getStringValue() +
            CipherSuites.TLS_RSA_WITH_RC4_128_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_AES_256_CBC_SHA.getStringValue()),
    NULL_CIPHERS(CipherSuites.TLS_ECDHE_RSA_WITH_NULL_SHA.getStringValue() + CipherSuites.TLS_ECDHE_ECDSA_WITH_NULL_SHA.getStringValue() +
            CipherSuites.TLS_ECDH_ANON_WITH_NULL_SHA.getStringValue() + CipherSuites.TLS_ECDH_RSA_WITH_NULL_SHA.getStringValue() +
            CipherSuites.TLS_ECDH_ECDSA_WITH_NULL_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_NULL_SHA256.getStringValue() +
            CipherSuites.TLS_RSA_WITH_NULL_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_NULL_MD5.getStringValue()),
    ANONYMOUS_NULL_CIPHERS(CipherSuites.TLS_ECDH_ANON_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_AES_256_GCM_SHA384.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_AES_256_CBC_SHA256.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_AES_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_CAMELLIA_256_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDH_ANON_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDH_ANON_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_AES_128_GCM_SHA256.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_AES_128_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_SEED_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_CAMELLIA_128_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDH_ANON_WITH_RC4_128_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_RC4_128_MD5.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_DES_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_EXPORT_WITH_RC4_40_MD5.getStringValue() +
            CipherSuites.TLS_ECDH_ANON_WITH_NULL_SHA.getStringValue()),
    ANONYMOUS_DH_CIPHERS(CipherSuites.TLS_DH_ANON_WITH_AES_256_GCM_SHA384.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_AES_256_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_CAMELLIA_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_AES_128_GCM_SHA256.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_AES_128_CBC_SHA256.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_SEED_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_CAMELLIA_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_WITH_RC4_128_MD5.getStringValue() + CipherSuites.TLS_DH_ANON_WITH_DES_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + CipherSuites.TLS_DH_ANON_EXPORT_WITH_RC4_40_MD5.getStringValue()),
    EXPORT_40_CIPHERS(CipherSuites.TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DH_ANON_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + CipherSuites.TLS_RSA_EXPORT_WITH_DES40_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_EXPORT_WITH_RC2_CBC_40_MD5.getStringValue() + CipherSuites.TLS_DH_ANON_EXPORT_WITH_RC4_40_MD5.getStringValue() +
            CipherSuites.TLS_RSA_EXPORT_WITH_RC4_40_MD5.getStringValue()),
    LOW_CIPHERS(CipherSuites.TLS_DHE_RSA_WITH_DES_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_DSS_WITH_DES_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_DES_CBC_SHA.getStringValue()),
    MEDIUM_CIPHERS(CipherSuites.TLS_DHE_RSA_WITH_SEED_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_DSS_WITH_SEED_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_SEED_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDHE_RSA_WITH_RC4_128_SHA.getStringValue() +
            CipherSuites.TLS_ECDHE_ECDSA_WITH_RC4_128_SHA.getStringValue() + CipherSuites.TLS_ECDH_RSA_WITH_RC4_128_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_RC4_128_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_RC4_128_MD5.getStringValue() +
            CipherSuites.TLS_PSK_WITH_RC4_128_SHA.getStringValue()),
    DES3_CIPHERS(CipherSuites.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            CipherSuites.TLS_SRP_SHA_DSS_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_SRP_SHA_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            CipherSuites.TLS_SRP_SHA_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDH_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_3DES_EDE_CBC_SHA.getStringValue() + CipherSuites.TLS_PSK_WITH_3DES_EDE_CBC_SHA.getStringValue()),
    HIGH_CIPHERS(CipherSuites.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384.getStringValue() + CipherSuites.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384.getStringValue() +
            CipherSuites.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384.getStringValue() + CipherSuites.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384.getStringValue() +
            CipherSuites.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_SRP_SHA_DSS_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_SRP_SHA_RSA_WITH_AES_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_SRP_SHA_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384.getStringValue() +
            CipherSuites.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384.getStringValue() + CipherSuites.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_DHE_DSS_WITH_AES_256_CBC_SHA256.getStringValue() + CipherSuites.TLS_DHE_RSA_WITH_AES_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DHE_DSS_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDH_RSA_WITH_AES_256_GCM_SHA384.getStringValue() +
            CipherSuites.TLS_ECDH_ECDSA_WITH_AES_256_GCM_SHA384.getStringValue() + CipherSuites.TLS_ECDH_RSA_WITH_AES_256_CBC_SHA384.getStringValue() +
            CipherSuites.TLS_ECDH_ECDSA_WITH_AES_256_CBC_SHA384.getStringValue() + CipherSuites.TLS_ECDH_RSA_WITH_AES_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_AES_256_GCM_SHA384.getStringValue() + CipherSuites.TLS_RSA_WITH_AES_256_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_RSA_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA.getStringValue() +
            CipherSuites.TLS_PSK_WITH_AES_256_CBC_SHA.getStringValue() + CipherSuites.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            CipherSuites.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256.getStringValue() + CipherSuites.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256.getStringValue() + CipherSuites.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_SRP_SHA_DSS_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_SRP_SHA_RSA_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256.getStringValue() + CipherSuites.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            CipherSuites.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256.getStringValue() + CipherSuites.TLS_DHE_DSS_WITH_AES_128_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_DHE_RSA_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_DSS_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA.getStringValue() + CipherSuites.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_ECDH_RSA_WITH_AES_128_GCM_SHA256.getStringValue() + CipherSuites.TLS_ECDH_ECDSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            CipherSuites.TLS_ECDH_RSA_WITH_AES_128_CBC_SHA256.getStringValue() + CipherSuites.TLS_ECDH_ECDSA_WITH_AES_128_CBC_SHA256.getStringValue() +
            CipherSuites.TLS_ECDH_RSA_WITH_AES_128_CBC_SHA.getStringValue() + CipherSuites.TLS_RSA_WITH_AES_128_GCM_SHA256.getStringValue() +
            CipherSuites.TLS_RSA_WITH_AES_128_CBC_SHA256.getStringValue() + CipherSuites.TLS_RSA_WITH_AES_128_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA.getStringValue() + CipherSuites.TLS_PSK_WITH_AES_128_CBC_SHA.getStringValue()),
    FREAK(CipherSuites.TLS_RSA_EXPORT1024_WITH_DES_CBC_SHA.getStringValue() + CipherSuites.TLS_RSA_EXPORT1024_WITH_RC4_56_SHA.getStringValue() +
            CipherSuites.TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA .getStringValue() + CipherSuites.TLS_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA.getStringValue() +
            CipherSuites.TLS_RSA_EXPORT_WITH_DES40_CBC_SHA.getStringValue() + CipherSuites.TLS_RSA_EXPORT_WITH_RC2_CBC_40_MD5.getStringValue() +
            CipherSuites.TLS_RSA_EXPORT_WITH_RC4_40_MD5 .getStringValue());


    private String value;

    TLSCipher(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
