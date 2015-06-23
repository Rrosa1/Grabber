package cl.tesis;

import java.io.IOException;
import java.security.cert.X509Certificate;

public class SSLUtil {

    public static Certificate getServerCertificate(String ip, boolean validate) throws SSLConnectionException, IOException{
        SSLConnection sslConnection = null;
        X509Certificate certificate;
        try {
            sslConnection =  new SSLConnection(ip, 443, validate);
            sslConnection.connect();
            certificate = (X509Certificate) sslConnection.getServerCertificate();
        } catch (SSLConnectionException e) {
            System.out.println("SSLConnectionException");
            throw e;
        } finally {
            if (sslConnection != null) {
                sslConnection.close();
            }
        }

        return new Certificate(certificate, ip, validate);
    }
}
