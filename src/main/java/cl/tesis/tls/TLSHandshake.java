package cl.tesis.tls;


import cl.tesis.https.TrustAllCert;
import cl.tesis.mail.SMTP;
import cl.tesis.mail.StartTLS;
import cl.tesis.mail.StartTLSProtocol;
import cl.tesis.tls.constant.TLSVersion;
import cl.tesis.tls.exception.*;

import javax.net.ssl.*;
import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

public class TLSHandshake implements Closeable{
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

    public TLSHandshake(StartTLSProtocol startTLSProtocol, StartTLS start, TLSVersion protocolVersion) throws SocketTLSHandshakeException, StartTLSException {
        try {
            // StartTLS
            Socket socket =  startTLSProtocol.getSocket();
            startProtocolHandshake(startTLSProtocol, start);

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

    public TLSHandshake(StartTLSProtocol startTLSProtocol, StartTLS start) throws SocketTLSHandshakeException, StartTLSException {
        this(startTLSProtocol, start, TLSVersion.TLS_12);
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

    @Override
    public void close() {
        if (socket != null)
            try{
                socket.close();
            } catch (IOException ignore) {
            } finally { socket = null; }
    }

    private void startProtocolHandshake(StartTLSProtocol startTLSProtocol, StartTLS start) throws StartTLSException {
        String response;
        InputStream in = startTLSProtocol.getIn();
        DataOutputStream out = startTLSProtocol.getOut();
        byte[] buffer = startTLSProtocol.getBuffer();
        try {
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

}
