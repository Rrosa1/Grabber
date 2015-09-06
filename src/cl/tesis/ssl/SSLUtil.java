package cl.tesis.ssl;

import java.security.cert.X509Certificate;

public class SSLUtil {

    public static HostCertificate getServerCertificate(String ip, boolean validate) throws Throwable {
        SSLConnection sslConnection = null;
        X509Certificate certificate;

        try {
            sslConnection =  new SSLConnection(ip, 443, validate);
            sslConnection.connect();
            certificate = (X509Certificate) sslConnection.getServerCertificate();
        } finally {
            if (sslConnection != null) {
                sslConnection.close();
            }
        }

        return new HostCertificate(certificate, ip, validate);
    }
}
