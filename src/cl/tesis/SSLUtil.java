package cl.tesis;

import java.io.IOException;
import java.security.cert.X509Certificate;

public class SSLUtil {

    public static Certificate getServerCertificate(String ip, boolean validate) throws SSLConnectionException, IOException {
        SSLConnection sslConnection =  new SSLConnection(ip, 443, validate);
        sslConnection.connect();
        X509Certificate certificates = (X509Certificate) sslConnection.getServerCertificate();
        return new Certificate(certificates, ip, validate);
    }
}
