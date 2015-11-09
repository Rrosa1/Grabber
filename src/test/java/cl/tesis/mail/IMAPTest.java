package cl.tesis.mail;

import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.ScanCiphersSuites;
import cl.tesis.tls.ScanTLSVersion;
import cl.tesis.tls.TLS;
import junit.framework.TestCase;

public class IMAPTest extends TestCase {
    public static String START_PROTOCOL = "* OK [CAPABILITY IMAP4REV1 I18NLEVEL=1 LITERAL+ SASL-IR LOGIN-REFERRALS STARTTLS LOGINDISABLED] dichato.dcc.uchile.cl";

    public IMAP imap;

    public void setUp() throws Exception {
        super.setUp();
        imap = new IMAP("192.80.24.4");
    }

   public void testIMAPProtocol() throws Exception {
        String start =  imap.readBanner();
        assertEquals(true, start.contains(START_PROTOCOL));
    }

    public void testHandshake() throws Exception {
        imap.readBanner();
        TLS tls =  new TLS(imap.getSocket());
        HostCertificate hostCertificate = tls.doProtocolHandshake(StartTLS.IMAP);
        assertEquals(true, hostCertificate.isValidation());
        assertEquals("RapidSSL SHA256 CA - G4", hostCertificate.getCertificateAuthority());
    }

    public void testAllTLSVersion() throws Exception {
        TLS tls =  new TLS(imap.getSocket());
        ScanTLSVersion version =  tls.checkTLSVersions(StartTLS.IMAP);
        assertEquals(false, version.isSSL_30());
        assertEquals(true, version.isTLS_10());
        assertEquals(false, version.isTLS_11());
        assertEquals(false, version.isTLS_12());
    }

    public void testCipherSuite() throws Exception {
        TLS tls =  new TLS(imap.getSocket());
        ScanCiphersSuites suites = tls.checkCipherSuites(StartTLS.IMAP);
        assertEquals("TLS_RSA_EXPORT_WITH_DES40_CBC_SHA", suites.getExport_40_ciphers());
        assertEquals("TLS_RSA_WITH_SEED_CBC_SHA", suites.getMedium_ciphers());
        assertEquals("TLS_RSA_WITH_3DES_EDE_CBC_SHA", suites.getDes3_ciphers());
        assertEquals("TLS_RSA_WITH_AES_256_CBC_SHA", suites.getHigh_ciphers());
    }

}
