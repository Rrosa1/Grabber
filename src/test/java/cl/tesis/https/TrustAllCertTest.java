package cl.tesis.https;

import cl.tesis.https.TrustAllCert;
import junit.framework.TestCase;
//import org.junit.Test;

import javax.net.ssl.TrustManager;
import java.security.cert.CertificateException;


public class TrustAllCertTest extends TestCase {

    public void testGetManager() throws Exception {
        TrustManager[] manager = TrustAllCert.getManager();
        assertTrue(manager[0] instanceof TrustAllCert);
    }

    public void testGetAcceptedIssuers() throws Exception {
        TrustAllCert cert = new TrustAllCert();
        assertEquals(cert.getAcceptedIssuers(), null);
    }

    public void testCheckClientTrusted() throws Exception {
        TrustAllCert cert = new TrustAllCert();
        Exception ex = null;
        try {
            cert.checkClientTrusted(null,null);
        }catch (CertificateException e) {
            ex = e;
        }
        assertNull(ex);
    }

    public void testCheckServerTrusted() throws Exception {
        TrustAllCert cert = new TrustAllCert();
        Exception ex = null;
        try {
            cert.checkServerTrusted(null,null);
        }catch (CertificateException e) {
            ex = e;
        }
        assertNull(ex);
    }


}