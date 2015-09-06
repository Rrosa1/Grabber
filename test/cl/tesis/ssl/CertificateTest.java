package cl.tesis.ssl;

import junit.framework.TestCase;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;


public class CertificateTest extends TestCase {

    private Certificate cert2048;
    private X509Certificate x509Cert;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        FileInputStream stream = new FileInputStream("test/resources/2048b-rsa-cert.pem");
        BufferedInputStream buffered = new BufferedInputStream(stream);

        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        x509Cert = (X509Certificate) cf.generateCertificate(buffered);
        cert2048 = new Certificate(x509Cert, "1.1.1.1", true);

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

    }

    public void testGetValueList() throws Exception {
        List<String> parameters = cert2048.getValueList();
        assertEquals(parameters.get(0), "1.1.1.1");
        assertEquals(parameters.get(1), "true");
        assertEquals(parameters.get(2), "SHA1withRSA");
        assertEquals(parameters.get(3), "Mon Aug 21 02:27:41 CLT 2017");
        assertEquals(parameters.get(4), "Frank4DD");
        assertEquals(parameters.get(5), "www.example.com");
        assertEquals(parameters.get(6), "Frank4DD Web CA");
        assertEquals(parameters.get(7), "2048");

    }
}