package cl.tesis.tls.handshake;

public class TLSCipherSuites {

    /* Initial state of a TLS connection during the first handshake */
    public static final String TLS_NULL_WITH_NULL_NULL = "0000";

    /* The following definitions require that the server provide an RSA certificate */
    public static final String TLS_RSA_WITH_NULL_MD5 = "0001";
    public static final String TLS_RSA_WITH_NULL_SHA = "0002";
    public static final String TLS_RSA_WITH_NULL_SHA256 = "003B";
    public static final String TLS_RSA_WITH_RC4_128_MD5 = "0004";
    public static final String TLS_RSA_WITH_RC4_128_SHA = "0005";
    public static final String TLS_RSA_WITH_3DES_EDE_CBC_SHA = "000A";
    public static final String TLS_RSA_WITH_AES_128_CBC_SHA = "002F";
    public static final String TLS_RSA_WITH_AES_256_CBC_SHA = "0035";
    public static final String TLS_RSA_WITH_AES_128_CBC_SHA256 = "003C";
    public static final String TLS_RSA_WITH_AES_256_CBC_SHA256 = "003D";



    /* The following cipher suite definitions are used from server-authenticate */
    public static final String TLS_DH_DSS_WITH_3DES_EDE_CBC_SHA = "000D";
    public static final String TLS_DH_RSA_WITH_3DES_EDE_CBC_SHA = "0010";
    public static final String TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA = "0013";
    public static final String TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA = "0016";
    public static final String TLS_DH_DSS_WITH_AES_128_CBC_SHA = "0030";
    public static final String TLS_DH_RSA_WITH_AES_128_CBC_SHA = "0031";
    public static final String TLS_DHE_DSS_WITH_AES_128_CBC_SHA = "0032";
    public static final String TLS_DHE_RSA_WITH_AES_128_CBC_SHA = "0033";
    public static final String TLS_DH_DSS_WITH_AES_256_CBC_SHA = "0036";
    public static final String TLS_DH_RSA_WITH_AES_256_CBC_SHA = "0037";
    public static final String TLS_DHE_DSS_WITH_AES_256_CBC_SHA = "0038";
    public static final String TLS_DHE_RSA_WITH_AES_256_CBC_SHA = "0039";
    public static final String TLS_DH_DSS_WITH_AES_128_CBC_SHA256 = "003E";
    public static final String TLS_DH_RSA_WITH_AES_128_CBC_SHA256 = "003F";
    public static final String TLS_DHE_DSS_WITH_AES_128_CBC_SHA256 = "0040";
    public static final String TLS_DHE_RSA_WITH_AES_128_CBC_SHA256 = "0067";
    public static final String TLS_DH_DSS_WITH_AES_256_CBC_SHA256 = "0068";
    public static final String TLS_DH_RSA_WITH_AES_256_CBC_SHA256 = "0069";
    public static final String TLS_DHE_DSS_WITH_AES_256_CBC_SHA256 = "006A";
    public static final String TLS_DHE_RSA_WITH_AES_256_CBC_SHA256 = "006B";

    /* The following cipher suites are used for completely anonymous Diffie-Hellman */
    public static final String TLS_DH_anon_WITH_RC4_128_MD5 = "0018";
    public static final String TLS_DH_anon_WITH_3DES_EDE_CBC_SHA = "001B";
    public static final String TLS_DH_anon_WITH_AES_128_CBC_SHA = "0034";
    public static final String TLS_DH_anon_WITH_AES_256_CBC_SHA = "003A";
    public static final String TLS_DH_anon_WITH_AES_128_CBC_SHA256 = "006C";
    public static final String TLS_DH_anon_WITH_AES_256_CBC_SHA256 = "006D";

    /* testing */
    public static final String TLS_ECDHE_RSA_WITH_RC4_128_SHA = "C011";


    public static final String test = TLS_DHE_RSA_WITH_AES_128_CBC_SHA + TLS_ECDHE_RSA_WITH_RC4_128_SHA + TLS_RSA_WITH_NULL_MD5 + TLS_RSA_WITH_NULL_SHA + TLS_RSA_WITH_NULL_SHA256 +
            TLS_RSA_WITH_RC4_128_MD5 + TLS_RSA_WITH_RC4_128_SHA + TLS_RSA_WITH_3DES_EDE_CBC_SHA + TLS_RSA_WITH_AES_128_CBC_SHA + TLS_RSA_WITH_AES_256_CBC_SHA;




}
