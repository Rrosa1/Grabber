package cl.tesis.ssl;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;


public class HostCertificateTest extends TestCase {

    private HostCertificate cert2048;
    private X509Certificate x509Cert;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        FileInputStream stream = new FileInputStream("test/resources/2048b-rsa-cert.pem");
        BufferedInputStream buffered = new BufferedInputStream(stream);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        x509Cert = (X509Certificate) cf.generateCertificate(buffered);
        cert2048 = new HostCertificate(x509Cert, "1.1.1.1", true, null);

    }

    public void testGetParameterList() throws Exception {
        List<String> parameters = cert2048.getParameterList();
        assertEquals(parameters.get(0), "Ip");
        assertEquals(parameters.get(1), "Validation");
        assertEquals(parameters.get(2), "Signature Algorithm");
        assertEquals(parameters.get(3), "Expired Time");
        assertEquals(parameters.get(4), "Organization Name");
        assertEquals(parameters.get(5), "Organization URL");
        assertEquals(parameters.get(6), "Certificate Authority");
        assertEquals(parameters.get(7), "Key Bits");
        assertEquals(parameters.get(8), "PEM Certificate");

    }

    public void testGetValueList() throws Exception {
        List<String> values = cert2048.getValueList();
        assertEquals(values.get(0), "1.1.1.1");
        assertEquals(values.get(1), "true");
        assertEquals(values.get(2), "SHA1withRSA");
        assertEquals(values.get(3), "Mon Aug 21 02:27:41 CLT 2017");
        assertEquals(values.get(4), "Frank4DD");
        assertEquals(values.get(5), "www.example.com");
        assertEquals(values.get(6), "Frank4DD Web CA");
        assertEquals(values.get(7), "2048");
    }
}