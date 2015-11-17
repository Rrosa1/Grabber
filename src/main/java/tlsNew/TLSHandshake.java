package tlsNew;


import cl.tesis.https.TrustAllCert;
import cl.tesis.mail.SMTP;
import cl.tesis.mail.StartTLS;
import cl.tesis.tls.HostCertificate;
import cl.tesis.tls.exception.StartTLSException;
import tlsNew.constant.TLSVersion;
import tlsNew.exception.SocketTLSHandshakeException;
import tlsNew.exception.TLSConnectionException;
import tlsNew.exception.TLSGetCertificateException;
import tlsNew.exception.TLSHandshakeException;

import javax.net.ssl.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
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

    private boolean provideSocket;

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

    public TLSHandshake(Socket socket, StartTLS start, TLSVersion protocolVersion) throws SocketTLSHandshakeException, StartTLSException {
        try {
            // StartTLS
            startProtocolHandshake(socket, start);

            // Wrap plain Socket
            SSLContext sslContext =  SSLContext.getInstance(protocolVersion.getName());
            sslContext.init(null, TrustAllCert.getManager(), new java.security.SecureRandom());
            this.sslSocketFactory = sslContext.getSocketFactory();

            this.socket = (SSLSocket) this.sslSocketFactory.createSocket(socket, socket.getInetAddress().getHostAddress(), socket.getPort(), true);
            this.provideSocket = true;

        } catch (NoSuchAlgorithmException | KeyManagementException | IOException e) {
            throw new SocketTLSHandshakeException();
        }
    }

    public TLSHandshake(String host, int port) throws SocketTLSHandshakeException {
        this(host, port, TLSVersion.TLS_12);
    }

    public TLSHandshake(Socket socket,StartTLS start) throws SocketTLSHandshakeException, StartTLSException {
        this(socket, start, TLSVersion.TLS_12);
    }

    public void connect() throws TLSHandshakeException, TLSConnectionException {
        if (!provideSocket) {
            try {
                this.socket.connect(this.address, CONNECTION_TIMEOUT);
            } catch (IOException e) {
                throw new TLSConnectionException();
            }
        }

        try {
            this.socket.setSoTimeout(HANDSHAKE_TIMEOUT);
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

    private void startProtocolHandshake(Socket socket, StartTLS start) throws StartTLSException {
        String response;
        try {
            InputStream in = socket.getInputStream();
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            byte[] buffer = new byte[2048];
            int readBytes;

            if (start == StartTLS.SMTP) {
                out.write(SMTP.EHLO.getBytes());
                readBytes = in.read(buffer);
                if (readBytes <= 0)
                    throw new StartTLSException();
            }

            out.write(start.getMessage().getBytes());
            readBytes = in.read(buffer);
            if (readBytes <= 0)
                throw new StartTLSException();

            response = new String(buffer, 0, readBytes);
            if (!response.contains(start.getResponse()))
                throw new StartTLSException();

        } catch (IOException e) {
            throw new StartTLSException();
        }
    }

    public static void main(String[] args) throws SocketTLSHandshakeException, IOException, TLSHandshakeException, TLSGetCertificateException, TLSConnectionException, StartTLSException {
        Socket socket =  new Socket ("192.80.24.4", 25);
        InputStream in = socket.getInputStream();
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        byte[] buffer =  new byte[2048];
        int readBytes = in.read(buffer);

        System.out.println(new String(buffer, 0, readBytes));

        TLSHandshake tls =  new TLSHandshake(socket, StartTLS.SMTP);
        tls.connect();
        X509Certificate[] certs = tls.getChainCertificate();
        HostCertificate h =  new HostCertificate(certs[0], false, certs);
        System.out.println(h.toJson());

        ScanTLSProtocols protocols = new ScanTLSProtocols("192.80.24.4", 25);
        System.out.println(protocols.scanAllProtocols(StartTLS.SMTP).toJson());
    }

}
