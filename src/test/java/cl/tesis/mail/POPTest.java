package cl.tesis.mail;

import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.ScanCiphersSuites;
import cl.tesis.tls.ScanTLSVersion;
import cl.tesis.tls.TLS;
import junit.framework.TestCase;

public class POPTest extends TestCase{
    public POP3 pop;

    public void setUp() throws Exception {
        super.setUp();
        pop = new POP3("64.64.18.121", 110);
    }

    public void testPOPProtocol() throws Exception {
        String start =  pop.startProtocol();
        assertEquals("+OK Dovecot ready.\r\n", start);
    }

    public void testHandshake() throws Exception {
        pop.startProtocol();
        TLS tls =  new TLS(pop.getSocket());
        HostCertificate hostCertificate = tls.doProtocolHandshake(StartTLS.POP3);
        assertEquals(true, hostCertificate.isValidation());
        assertEquals("Go Daddy Secure Certificate Authority - G2", hostCertificate.getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        TLS tls =  new TLS(pop.getSocket());
        ScanTLSVersion version =  tls.checkTLSVersions(StartTLS.POP3);
        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(false, version.isTLS_11());
        assertEquals(false, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        TLS tls =  new TLS(pop.getSocket());
        ScanCiphersSuites suites = tls.checkCipherSuites(StartTLS.POP3);
        assertEquals("TLS_RSA_WITH_RC4_128_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }
}
