package cl.tesis.tls;

import junit.framework.TestCase;

import java.security.cert.X509Certificate;

public class TLSTest extends TestCase{

    public static final String HOST = "192.80.24.4";
    public static final int PORT = 443;

    public void testHandshake() throws Exception {
        TLSHandshake tlsHandshake =  new TLSHandshake(HOST, PORT);
        tlsHandshake.connect();
        X509Certificate[] certs = tlsHandshake.getChainCertificate();
        Certificate[] chain = Certificate.parseCertificateChain(certs);

//        assertEquals(true, certificate.isValidation()); // Not implement yet
        assertEquals("SHA256withRSA", chain[0].getSignatureAlgorithm());
        assertEquals("*.dcc.uchile.cl", chain[0].getOrganizationURL());
        assertEquals("2048", chain[0].getKeyBits());
        assertEquals("RapidSSL SHA256 CA - G4", chain[0].getCertificateAuthority());
    }

    public void testTLSVersion() throws Exception {
        ScanTLSProtocols protocols =  new ScanTLSProtocols(HOST, PORT);
        ScanTLSProtocolsData version = protocols.scanAllProtocols();

        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(true, version.isTLS_11());
        assertEquals(true, version.isTLS_12());
    }

    public void testCipherSuites() throws Exception {
        ScanCipherSuites cipherSuites =  new ScanCipherSuites(HOST, PORT);
        ScanCipherSuitesData suites = cipherSuites.scanAllCipherSuites();

        assertEquals("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }

//    public void testHeartbeat() throws Exception {
//        ScanHeartbleed scanHeartbleed =  new ScanHeartbleed(HOST, PORT);
//        HeartbleedData heartbleed = scanHeartbleed.hasHeartbleed();
//
//        assertEquals(true, heartbleed.isHeartbeat());
//    }
}
