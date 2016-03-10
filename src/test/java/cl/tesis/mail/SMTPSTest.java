package cl.tesis.mail;


import cl.tesis.tls.*;
import junit.framework.TestCase;

import java.security.cert.X509Certificate;

public class SMTPSTest extends TestCase{
    public static final String HOST = "192.80.24.4";
    public static final int PORT = 465;

    public void testHandshake() throws Exception {
        TLSHandshake tlsHandshake =  new TLSHandshake(HOST, PORT);
        tlsHandshake.connect();
        X509Certificate[] certs = tlsHandshake.getChainCertificate();

        Certificate[] chain = Certificate.parseCertificateChain(certs);
        assertEquals("RapidSSL SHA256 CA - G4", chain[0].getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        ScanTLSProtocols protocols =  new ScanTLSProtocols(HOST, PORT);
        ScanTLSProtocolsData version = protocols.scanAllProtocols();

        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(true, version.isTLS_11());
        assertEquals(true, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        ScanCipherSuites cipherSuites = new ScanCipherSuites(HOST, PORT);
        ScanCipherSuitesData suites = cipherSuites.scanAllCipherSuites();

        assertEquals("TLS_ECDH_ANON_WITH_AES_256_CBC_SHA", suites.getAnonymous_null_ciphers());
        assertEquals("TLS_DH_ANON_WITH_AES_256_CBC_SHA", suites.getAnonymous_dh_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_SEED_CBC_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }
}
