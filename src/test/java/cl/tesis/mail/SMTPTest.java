package cl.tesis.mail;


import cl.tesis.tls.*;
import junit.framework.TestCase;

import java.security.cert.X509Certificate;


public class SMTPTest extends TestCase{
    public static final String HOST = "192.80.24.4";
    public static final int PORT = 25;
    public SMTP smtp;

    public void setUp() throws Exception {
        super.setUp();
        smtp = new SMTP(HOST, PORT);
    }

    public void testSMTPProtocol() throws Exception {
        String start =  smtp.readBanner();
        assertEquals("220 dichato.dcc.uchile.cl ESMTP Postfix\r\n", start);

        String help =  smtp.sendHELP();
        assertEquals("502 5.5.2 Error: command not recognized\r\n", help);

        String ehlo =  smtp.sendEHLO();
        assertEquals("250-dichato.dcc.uchile.cl\r\n250-PIPELINING\r\n250-SIZE 120480000\r\n250-VRFY\r\n250-ETRN\r\n250-STARTTLS\r\n250-ENHANCEDSTATUSCODES\r\n250-8BITMIME\r\n250 DSN\r\n", ehlo);
    }

    public void testHandshake() throws Exception {
        smtp.readBanner();
        smtp.sendEHLO();

        TLSHandshake tlsHandshake =  new TLSHandshake(smtp, StartTLS.SMTP);
        tlsHandshake.connect();
        X509Certificate[] certs = tlsHandshake.getChainCertificate();

        Certificate[] chain = Certificate.parseCertificateChain(certs);
//        assertEquals(true, hostCertificate.isValidation()); // TODO Implement validation
        assertEquals("RapidSSL SHA256 CA - G4", chain[0].getCertificateAuthority());
    }

   public void testAllTLSVersion() throws Exception {
        ScanTLSProtocols protocols =  new ScanTLSProtocols(HOST, PORT);
        ScanTLSProtocolsData version = protocols.scanAllProtocols(StartTLS.SMTP);

        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(true, version.isTLS_11());
        assertEquals(true, version.isTLS_12());
   }

    public void testCipherSuite() throws Exception {
        ScanCipherSuites cipherSuites = new ScanCipherSuites(HOST, PORT);
        ScanCipherSuitesData suites = cipherSuites.scanAllCipherSuites(StartTLS.SMTP);

        assertEquals("TLS_ECDH_ANON_WITH_AES_256_CBC_SHA", suites.getAnonymous_null_ciphers());
        assertEquals("TLS_DH_ANON_WITH_AES_256_CBC_SHA", suites.getAnonymous_dh_ciphers());
        assertEquals("TLS_DHE_RSA_WITH_SEED_CBC_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }

    public void testHeartbeat() throws Exception {
        ScanHeartbleed scanHeartbleed =  new ScanHeartbleed(HOST, PORT);
        HeartbleedData heartbleed = scanHeartbleed.hasHeartbleed(StartTLS.SMTP);

        assertEquals(true, heartbleed.isHeartbeat());
    }
}
