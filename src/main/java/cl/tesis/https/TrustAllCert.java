package cl.tesis.https;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public class TrustAllCert implements X509TrustManager{
    private static TrustManager[] trustManagers = null;
    private static HostnameVerifier verifier = null;

    public TrustAllCert() {
    }

    @Override
    public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {

    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return null;
    }

    public static  TrustManager[] getManager(){
        if (trustManagers == null) {
            trustManagers = new TrustManager[]{new TrustAllCert()};
        }
        return trustManagers;
    }

    public static HostnameVerifier getHostnameVerifier() {
        if (verifier == null) {
            verifier = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };
        }
        return verifier;
    }
}
