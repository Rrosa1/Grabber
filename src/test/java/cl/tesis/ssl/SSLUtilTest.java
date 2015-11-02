package cl.tesis.ssl;

import junit.framework.TestCase;

public class SSLUtilTest extends TestCase {

    public void testGetServerCertificate() throws Throwable {
        HostCertificate cert = SSLUtil.getServerCertificate("64.233.190.94", false);
        assertEquals("google.com", cert.getOrganizationURL());

        cert = SSLUtil.getServerCertificate("64.233.190.94", true);
        assertEquals("google.com", cert.getOrganizationURL());
    }
}