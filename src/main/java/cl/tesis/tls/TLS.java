package cl.tesis.tls;

import cl.tesis.mail.SMTP;
import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.handshake.*;
import tlsNew.HeartbleedData;
import tlsNew.ScanCipherSuitesData;
import tlsNew.ScanTLSProtocolsData;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import static cl.tesis.tls.CertChainValidator.validateKeyChain;

public class TLS {

    private static final int BUFFER_SIZE = 8196;
    private static final int TIMEOUT = 5000;
    private static final String TLS_ALERT = "150300020240";

    private Socket socket;
    private InputStream in;
    private DataOutputStream out;
    private byte[] buffer;
    public X509Certificate[] c;

    public TLS(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setSoTimeout(TIMEOUT);
        this.in = this.socket.getInputStream();
        this.out = new DataOutputStream(this.socket.getOutputStream());
        this.buffer = new byte[BUFFER_SIZE];
    }

    public HostCertificate doHandshake() throws IOException, HandshakeException {
        HostCertificate hostCertificate = null;
        try {
            /* client hello */
            this.out.write(new ClientHello(TLSVersion.TLS_11.getStringVersion(), TLSCipher.TEST).toByte());
            this.readAllAvailable();

            /* server hello */
            try{
            ServerHello serverHello = new ServerHello(buffer);

            /* certificate */
                CertificateMessage certificateMessage = new CertificateMessage(buffer, serverHello.endOfServerHello());
                hostCertificate = this.byteArrayToHostCertificate(certificateMessage.getCertificates());

            } catch (IndexOutOfBoundsException e){
                System.out.println(this.socket.getInetAddress());
            }

            /* Close connection */
            this.sendAlertMessage();

        } catch (TLSHeaderException e) {
             throw new HandshakeException("Error in TLS header");
        } catch (HandshakeHeaderException e) {
            throw  new HandshakeException("Error in handshake header");
        }
        return hostCertificate;
    }

    public HostCertificate doProtocolHandshake(StartTLS start) throws StartTLSException, IOException, HandshakeException {
        if (!this.startProtocolHandshake(start)) {
            throw new StartTLSException(String.format("Problems to start TLS handshake of %s", start.getProtocol()));
        }

        return this.doHandshake();
    }

    public ScanTLSProtocolsData checkTLSVersions(StartTLS start) {
        ScanTLSProtocolsData scanTLSProtocolsData = new ScanTLSProtocolsData();
        InetAddress address = this.socket.getInetAddress();
        ServerHello serverHello;
        int port = this.socket.getPort();

        for (TLSVersion tls : TLSVersion.values()) {
            try {
                /* Open and setting the connection */
                this.newConnection(address, port);

                /* STARTTLS */
                if (start != null) {
                    this.readFirstLine();
                    this.startProtocolHandshake(start);
                }

                this.out.write(new ClientHello(tls.getStringVersion(), TLSCipher.TEST).toByte());
                this.in.read(buffer);
                serverHello = new ServerHello(buffer);
                this.sendAlertMessage();


            } catch (TLSHeaderException | HandshakeHeaderException | IOException e) {
                scanTLSProtocolsData.setTLSVersion(tls, false);
                continue;
            }

            if (Arrays.equals(tls.getByteVersion(), serverHello.getProtocolVersion())) {
                scanTLSProtocolsData.setTLSVersion(tls, true);
            } else {
                scanTLSProtocolsData.setTLSVersion(tls, false);
            }
        }

        return scanTLSProtocolsData;
    }

    public ScanCipherSuitesData checkCipherSuites(StartTLS start) {
        ScanCipherSuitesData scanCipherSuitesData = new ScanCipherSuitesData();
        InetAddress address = this.socket.getInetAddress();
        ServerHello serverHello;
        int port = this.socket.getPort();

        for (TLSCipher cipher : TLSCipher.values()) {
            try {
                /* Open and setting the connection */
                this.newConnection(address, port);

                /* STARTTLS */
                if (start != null) {
                    this.readFirstLine();
                    this.startProtocolHandshake(start);
                }

                this.out.write(new ClientHello(TLSVersion.TLS_11.getStringVersion(), cipher).toByte());
                this.in.read(buffer);
                serverHello = new ServerHello(buffer);
                this.sendAlertMessage();

            } catch (TLSHeaderException | HandshakeHeaderException | IOException e) {
                scanCipherSuitesData.setCipherSuite(cipher, null);
                continue;
            }

            scanCipherSuitesData.setCipherSuite(cipher, CipherSuites.getNameByByte(serverHello.getCipherSuite()));
        }

        return scanCipherSuitesData;
    }

    public HeartbleedData heartbleedTest(StartTLS start, TLSVersion version) {
        HeartbleedData heartbleed;
        try {
            InetAddress address = this.socket.getInetAddress();
            int port = this.socket.getPort();
            this.newConnection(address, port);

            if (start != null) {
                this.readFirstLine();
                this.startProtocolHandshake(start);
            }

            this.out.write(ClientHello.heartbleedHello(version));
            this.in.read(buffer);

            ServerHello serverHello = new ServerHello(buffer);
            heartbleed = new HeartbleedData(serverHello.hasHeartbeat());
        } catch (TLSHeaderException | HandshakeHeaderException | IOException e) {
            return new HeartbleedData(false);
        }
        return heartbleed;
    }

    private void readFirstLine() throws IOException {
        this.socket.setSoTimeout(TIMEOUT * 2);
        try {
            in.read(this.buffer);
        } catch (SocketTimeoutException ignored){

        } finally {
            this.socket.setSoTimeout(TIMEOUT);
        }
    }

    private void newConnection(InetAddress address, int port) throws IOException {
        /* CLose the old socket */
        this.socket.close();

        /* Setting new socket */
        this.socket = new Socket(address, port);
        this.socket.setSoTimeout(TIMEOUT);
        this.in = socket.getInputStream();
        this.out =  new DataOutputStream(socket.getOutputStream());
    }

    private boolean startProtocolHandshake(StartTLS start) throws IOException {
        if (start == StartTLS.SMTP) {
            this.out.write(SMTP.EHLO.getBytes());
            // wait 5000
            int readBytes = in.read(buffer);
            if (readBytes <= 0)
                return false;
        }

        this.out.write(start.getMessage().getBytes());
        int readBytes = this.in.read(this.buffer);
        if (readBytes <= 0)
            return false;

        String response = new String(this.buffer, 0, readBytes);

        return response.contains(start.getResponse());
    }

    private int readAllAvailable() throws IOException{
        int totalRead = 0;
        int readBytes;
        try {
            do {
                readBytes = in.read(this.buffer, totalRead, BUFFER_SIZE - totalRead);
                totalRead += readBytes;
            } while (readBytes > 0);
        } catch (SocketTimeoutException e) {
            /* Finished to read the input */
        }
        return totalRead;
    }

    private HostCertificate byteArrayToHostCertificate(List<byte[]> list) {
        HostCertificate certificate;

        try {
            X509Certificate[] certs = new X509Certificate[list.size()];
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            int i = 0;

            for (byte[] array : list) {
                InputStream in = new ByteArrayInputStream(array);
                certs[i] = (X509Certificate) certificateFactory.generateCertificate(in);
                ++i;
            }

            X509Certificate[] chain;
            if (i == 1) {
                chain = null;
            } else {
                chain = Arrays.copyOfRange(certs, 1, certs.length);
            }

            c = certs;
            certificate = new HostCertificate(certs[0], validateKeyChain(certs[0], chain), chain);
        } catch (CertificateException | InvalidAlgorithmParameterException | NoSuchAlgorithmException | NoSuchProviderException e) {
            return null;
        }
        return certificate;

        }

    private void sendAlertMessage() throws IOException {
        this.out.write(TLSUtil.hexStringToByteArray(TLS_ALERT));
    }

    public static void main(String[] args) throws IOException, TLSHeaderException, HandshakeHeaderException, HandshakeException {
        Socket socket = new Socket("200.91.20.147", 443);
        TLS tls =  new TLS(socket);
        tls.doHandshake();
    }
}
