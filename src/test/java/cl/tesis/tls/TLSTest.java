package cl.tesis.tls;

import cl.tesis.tls.handshake.TLSVersion;
import junit.framework.TestCase;

import java.net.Socket;

public class TLSTest extends TestCase{
    public TLS tls;

    public void setUp() throws Exception {
        super.setUp();
        tls = new TLS(new Socket("192.80.24.4", 443));
    }

    public void testHandshake() throws Exception {
        HostCertificate certificate =  tls.doHandshake();
        assertEquals(true, certificate.isValidation());
        assertEquals("SHA256withRSA", certificate.getSignatureAlgorithm());
        assertEquals("*.dcc.uchile.cl", certificate.getOrganizationURL());
        assertEquals("2048", certificate.getKeyBits());
        assertEquals("RapidSSL SHA256 CA - G4", certificate.getCertificateAuthority());
    }

    public void testTLSVersion() throws Exception {
        ScanTLSVersion version =  tls.checkTLSVersions(null);
        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(true, version.isTLS_11());
        assertEquals(true, version.isTLS_12());
    }

    public void testCipherSuites() throws Exception {
        ScanCiphersSuites suites = tls.checkCipherSuites(null);
        assertEquals("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }

    public void testHeartbeat() throws Exception {
        Heartbleed heartbleed =  tls.heartbleedTest(null, TLSVersion.TLS_12);
        assertEquals(true, heartbleed.isHeartbeat());
    }
}
