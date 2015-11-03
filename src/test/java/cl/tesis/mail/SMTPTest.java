package cl.tesis.mail;


import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.ScanCiphersSuites;
import cl.tesis.tls.ScanTLSVersion;
import cl.tesis.tls.TLS;
import junit.framework.TestCase;



public class SMTPTest extends TestCase{
    public SMTP smtp;

    public void setUp() throws Exception {
        super.setUp();
        smtp = new SMTP("192.80.24.4", 25);
    }

    public void testSMTPProtocol() throws Exception {
        String start =  smtp.startProtocol();
        assertEquals("220 dichato.dcc.uchile.cl ESMTP Postfix\r\n", start);

        String help =  smtp.sendHELP();
        assertEquals("502 5.5.2 Error: command not recognized\r\n", help);

        String ehlo =  smtp.sendEHLO();
        assertEquals("250-dichato.dcc.uchile.cl\r\n250-PIPELINING\r\n250-SIZE 120480000\r\n250-VRFY\r\n250-ETRN\r\n250-STARTTLS\r\n250-ENHANCEDSTATUSCODES\r\n250-8BITMIME\r\n250 DSN\r\n", ehlo);
    }

    public void testHandshake() throws Exception {
        smtp.startProtocol();
        smtp.sendEHLO();
        TLS tls =  new TLS(smtp.getSocket());
        HostCertificate hostCertificate = tls.doProtocolHandshake(StartTLS.SMTP);
        assertEquals(true, hostCertificate.isValidation());
        assertEquals("RapidSSL SHA256 CA - G4", hostCertificate.getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        TLS tls =  new TLS(smtp.getSocket());
        ScanTLSVersion version =  tls.checkTLSVersions(StartTLS.SMTP);
        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(true, version.isTLS_11());
        assertEquals(true, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        TLS tls =  new TLS(smtp.getSocket());
        ScanCiphersSuites suites = tls.checkCipherSuites(StartTLS.SMTP);
        assertEquals("TLS_ECDH_ANON_WITH_AES_256_CBC_SHA", suites.getAnonymous_null_ciphers());
        assertEquals("TLS_DH_ANON_WITH_AES_256_CBC_SHA", suites.getAnonymous_dh_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_SEED_CBC_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }
}
