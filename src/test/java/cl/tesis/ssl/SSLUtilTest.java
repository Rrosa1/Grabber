package cl.tesis.ssl;

import cl.tesis.ssl.ssl.HostCertificate;
import cl.tesis.ssl.ssl.SSLUtil;
import junit.framework.TestCase;

public class SSLUtilTest extends TestCase {

    public void testGetServerCertificate() throws Throwable {
        HostCertificate cert = SSLUtil.getServerCertificate("200.10.226.222", false);
        assertEquals("google.com", cert.getOrganizationURL());

        cert = SSLUtil.getServerCertificate("200.10.226.222", true);
        assertEquals("google.com", cert.getOrganizationURL());
    }
}