package cl.tesis;

import javax.net.ssl.*;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.Certificate;


public class SSLConnection {
    private static final int DEFAULT_TIMEOUT = 120;
    private static final int MILLISECONDS = 1000;

    private SSLSocketFactory factory;
    private SSLSocket socket;
    private SSLSession session;
    private SocketAddress address;
    private int timeout;

    public SSLConnection(String host, int port, boolean validate) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        this(host, port, validate, DEFAULT_TIMEOUT);
    }

    public SSLConnection(String host, int port, boolean validate, int timeout) throws IOException, NoSuchAlgorithmException, KeyManagementException {
        if (validate) {
            this.factory = HttpsURLConnection.getDefaultSSLSocketFactory();
        } else {
            SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, TrustAllCert.getManager(), new java.security.SecureRandom());
            this.factory = sslContext.getSocketFactory();
        }
        this.socket = (SSLSocket) this.factory.createSocket();
        this.address = new InetSocketAddress(host, port);
        this.timeout = timeout;
    }

    public void connect() throws IOException {
        if (this.timeout <= 0) {
            this.socket.connect(this.address, this.timeout * MILLISECONDS);
        } else {
            this.socket.connect(this.address);
        }
        this.socket.startHandshake();
        this.session = this.socket.getSession();
    }

    public Certificate getServerCertificate() throws SSLPeerUnverifiedException {
        return this.session.getPeerCertificates()[0];
    }

}