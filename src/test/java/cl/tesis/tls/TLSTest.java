package cl.tesis.tls;

import junit.framework.TestCase;
import tlsNew.*;

import java.net.Socket;
import java.security.cert.X509Certificate;

public class TLSTest extends TestCase{

    public static final String HOST = "192.80.24.4";
    public static final int PORT = 443;
    public TLS tls;

    public void setUp() throws Exception {
        super.setUp();
        tls = new TLS(new Socket(HOST, PORT));
    }

    public void testHandshake() throws Exception {
        TLSHandshake tlsHandshake =  new TLSHandshake(HOST, PORT);
        tlsHandshake.connect();
        X509Certificate[] certs = tlsHandshake.getChainCertificate();
        HostCertificate certificate =  new HostCertificate(certs[0], true, certs);
//        HostCertificate certificate =  tls.doHandshake();
        assertEquals(true, certificate.isValidation()); // Not implement yet
        assertEquals("SHA256withRSA", certificate.getSignatureAlgorithm());
        assertEquals("*.dcc.uchile.cl", certificate.getOrganizationURL());
        assertEquals("2048", certificate.getKeyBits());
        assertEquals("RapidSSL SHA256 CA - G4", certificate.getCertificateAuthority());
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
