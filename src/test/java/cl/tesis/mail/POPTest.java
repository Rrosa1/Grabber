package cl.tesis.mail;

import cl.tesis.tls.*;
import junit.framework.TestCase;

import java.security.cert.X509Certificate;

public class POPTest extends TestCase{
    public static final String HOST = "64.64.18.121";
    public static final int PORT = 110;
    public POP3 pop;

    public void setUp() throws Exception {
        super.setUp();
        pop = new POP3(HOST, PORT);
    }

    public void testPOPProtocol() throws Exception {
        String start =  pop.readBanner();
        assertEquals("+OK Dovecot ready.\r\n", start);
    }

    public void testHandshake() throws Exception {
        pop.readBanner();

        TLSHandshake tlsHandshake =  new TLSHandshake(pop, StartTLS.POP3);
        tlsHandshake.connect();
        X509Certificate[] certs =  tlsHandshake.getChainCertificate();

        Certificate[] chain = Certificate.parseCertificateChain(certs);
        assertEquals("Go Daddy Secure Certificate Authority - G2", chain[0].getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        ScanTLSProtocols protocols = new ScanTLSProtocols(HOST, PORT);
        ScanTLSProtocolsData version =  protocols.scanAllProtocols(StartTLS.POP3);

        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(false, version.isTLS_11());
        assertEquals(false, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        ScanCipherSuites cipherSuites = new ScanCipherSuites(HOST, PORT);
        ScanCipherSuitesData suites = cipherSuites.scanAllCipherSuites(StartTLS.POP3);

        assertEquals("TLS_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }
}
