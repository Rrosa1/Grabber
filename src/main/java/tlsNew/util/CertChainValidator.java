package tlsNew.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.*;
import java.security.cert.Certificate;
import java.util.*;

public class CertChainValidator {

    private static String keyStorePath = System.getProperty("java.home") + "/lib/security/cacerts".replace('/', File.separatorChar);
    private static String password = "changeit";

    public static KeyStore keyStore = null;

    /**
     * Validate keychain
     *
     * @param client   is the client X509Certificate
     * @param keyStore containing all trusted certificate
     * @return true if validation until root certificate success, false otherwise
     * @throws KeyStoreException
     * @throws CertificateException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static boolean validateKeyChain(X509Certificate client,
                                           KeyStore keyStore) throws KeyStoreException, CertificateException,
            InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException {
        X509Certificate[] certs = new X509Certificate[keyStore.size()];
        int i = 0;
        Enumeration<String> alias = keyStore.aliases();

        while (alias.hasMoreElements()) {
            certs[i++] = (X509Certificate) keyStore.getCertificate(alias
                    .nextElement());
        }

        return validateKeyChain(client, certs);
    }

    /**
     * Validate keychain
     *
     * @param client       is the client X509Certificate
     * @param trustedCerts is Array containing all trusted X509Certificate
     * @return true if validation until root certificate success, false otherwise
     * @throws CertificateException
     * @throws InvalidAlgorithmParameterException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static boolean validateKeyChain(X509Certificate client, X509Certificate[] trustedCerts) throws CertificateException, InvalidAlgorithmParameterException, NoSuchAlgorithmException,
            NoSuchProviderException {
        if (trustedCerts == null){
            return isRootCertificate(client);
        }

        boolean found = false;
        int i = trustedCerts.length;
        CertificateFactory cf = CertificateFactory.getInstance("X.509");
        TrustAnchor anchor;
        Set anchors;
        CertPath path;
        List list;
        PKIXParameters params;
        CertPathValidator validator = CertPathValidator.getInstance("PKIX");

        while (!found && i > 0) {
            anchor = new TrustAnchor(trustedCerts[--i], null);
            anchors = Collections.singleton(anchor);

            list = Arrays.asList(new Certificate[]{client});
            path = cf.generateCertPath(list);

            params = new PKIXParameters(anchors);
            params.setRevocationEnabled(false);

            if (client.getIssuerDN().equals(trustedCerts[i].getSubjectDN())) {
                try {
                    validator.validate(path, params);
                    if (isSelfSigned(trustedCerts[i])) {
                        // found root ca
                        found = true;
                    } else if (!client.equals(trustedCerts[i])) {
                        // find parent ca
                        found = validateKeyChain(trustedCerts[i], trustedCerts);
                    }
                } catch (CertPathValidatorException e) {
                    // validation fail, check next certifiacet in the trustedCerts array
                }
            }
        }

        if (!found) {
            try {
                // Check if server not send root certificate
                KeyStore keystore = getKeyStore();

                Enumeration enumeration = keystore.aliases();
                while (enumeration.hasMoreElements()) {
                    String alias = (String) enumeration.nextElement();
                    X509Certificate certificate = (X509Certificate) keystore.getCertificate(alias);
                    if (certificate.getSubjectDN().equals(client.getIssuerDN()))
                        return true;
                }
            } catch (KeyStoreException e) {
                return found;
            }
        }
        return found;
    }

    /**
     * @param cert is X509Certificate that will be tested
     * @return true if cert is self signed, false otherwise
     * @throws CertificateException
     * @throws NoSuchAlgorithmException
     * @throws NoSuchProviderException
     */
    public static boolean isSelfSigned(X509Certificate cert) throws CertificateException, NoSuchAlgorithmException, NoSuchProviderException {
        try {
            PublicKey key = cert.getPublicKey();
            cert.verify(key);
        } catch (SignatureException sigEx) {
            return false;
        } catch (InvalidKeyException keyEx) {
            return false;
        }

        return isRootCertificate(cert);
    }

    public static KeyStore getKeyStore() throws KeyStoreException {
        if (keyStore == null ) {
            try {
                FileInputStream is = new FileInputStream(keyStorePath);
                keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
                keyStore.load(is, password.toCharArray());
            } catch (CertificateException | NoSuchAlgorithmException | KeyStoreException | IOException e) {
                keyStore = null;
                throw new KeyStoreException();
            }
        }
        return keyStore;
    }


    private static boolean isRootCertificate(X509Certificate certificate) {
        String inKeyStore;

        try {
            KeyStore keystore = getKeyStore();
            if (keystore == null)
                return false;

            inKeyStore = keystore.getCertificateAlias(certificate);
        } catch (KeyStoreException e) {
            return false;
        }

        return inKeyStore != null;
    }

    public static void main(String[] args) throws KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
//        FileInputStream is = new FileInputStream(keyStorePath);
//        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
//        keystore.load(is, password.toCharArray());
////
////        Enumeration<String> s = keystore.aliases();
////
////        while(s.hasMoreElements()){
////            System.out.println(s.nextElement());
////        }
        KeyStore keystore = getKeyStore();

        Enumeration enumeration = keystore.aliases();
        while(enumeration.hasMoreElements()) {
            String alias = (String)enumeration.nextElement();
            System.out.println("alias name: " + alias);
            Certificate certificate = keystore.getCertificate(alias);
            System.out.println(certificate.toString());

        }


    }
}