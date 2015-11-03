package cl.tesis.ssl;

import cl.tesis.tls.HostCertificate;

import java.security.cert.X509Certificate;

public class SSLUtil {

    public static HostCertificate getServerCertificate(String ip, boolean validate) throws Throwable {
        SSLConnection sslConnection = null;
        X509Certificate certificate;
        X509Certificate[] chain;

        try {
            sslConnection =  new SSLConnection(ip, 443, validate);
            sslConnection.connect();
            certificate = sslConnection.getServerCertificate();
            chain = sslConnection.getChainCertificates();
        } finally {
            if (sslConnection != null) {
                sslConnection.close();
            }
        }

        return new HostCertificate(certificate, validate, chain);
    }
}
