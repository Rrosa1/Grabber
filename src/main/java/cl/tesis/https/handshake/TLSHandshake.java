package cl.tesis.https.handshake;


import cl.tesis.https.TrustAllCert;
import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.handshake.TLSVersion;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class TLSHandshake {
    private static final int MILLISECONDS = 1000;
    private static final int CONNECTION_TIMEOUT = 60 * MILLISECONDS; // 60 seg
    private static final int HANDSHAKE_TIMEOUT = 120 * MILLISECONDS; // 120 seg

    private SSLSocketFactory sslSocketFactory;
    private SSLSocket socket;
    private SSLSession session;
    private SocketAddress address;

    public TLSHandshake(String host, int port, TLSVersion protocolVersion) throws SocketTLSHandshakeException {
        try {

            SSLContext sslContext =  SSLContext.getInstance(protocolVersion.getName());
            sslContext.init(null, TrustAllCert.getManager(), new java.security.SecureRandom());
            this.sslSocketFactory = sslContext.getSocketFactory();

            this.socket = (SSLSocket) this.sslSocketFactory.createSocket();
            this.address = new InetSocketAddress(host, port);

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new SocketTLSHandshakeException();
        }
    }

    public TLSHandshake(String host, int port) throws SocketTLSHandshakeException {
        this(host, port, TLSVersion.TLS_12);
    }

    public void connect() throws TLSHandshakeException, TLSConnectionException {
        try {
            this.socket.connect(this.address, CONNECTION_TIMEOUT);
            this.socket.setSoTimeout(HANDSHAKE_TIMEOUT);
        } catch (IOException e) {
            throw new TLSConnectionException();
        }
        try {
            this.socket.startHandshake();
            this.session = this.socket.getSession();
        } catch (IOException e) {
            throw new TLSHandshakeException();
        }

    }

    public X509Certificate[] getChainCertificate() throws TLSGetCertificateException {

        X509Certificate[] certificate;

        try {
            certificate = (X509Certificate[]) this.session.getPeerCertificates();
        } catch (SSLPeerUnverifiedException e) {
            throw new TLSGetCertificateException();
        }

        return certificate;
    }

    public String getProtocol() {
        return this.session.getProtocol();
    }

    public String getCipherSuite() {
        return this.session.getCipherSuite();
    }

    public static void main(String[] args) throws SocketTLSHandshakeException, IOException, TLSHandshakeException, TLSGetCertificateException, TLSConnectionException {
        TLSHandshake tls =  new TLSHandshake("192.80.24.4", 443);
        tls.connect();
        X509Certificate[] certs = tls.getChainCertificate();
        HostCertificate h =  new HostCertificate(certs[0], false, certs);
        System.out.println(h.toJson());
    }

}
