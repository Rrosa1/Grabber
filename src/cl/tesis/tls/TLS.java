package cl.tesis.tls;

import cl.tesis.mail.SMTP;
import cl.tesis.mail.SMTPData;
import cl.tesis.mail.StartTLS;
import cl.tesis.ssl.HostCertificate;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.List;

import static cl.tesis.tls.CertChainValidator.validateKeyChain;

public class TLS {

    private static final int BUFFER_SIZE = 8196;
    public static final int TIMEOUT = 5000;

    private Socket socket;
    private InputStream in;
    private DataOutputStream out;
    private byte[] buffer;

    public TLS(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setSoTimeout(5000);
        this.in = this.socket.getInputStream();
        this.out = new DataOutputStream(this.socket.getOutputStream());
        this.buffer = new byte[BUFFER_SIZE];
    }

    public HostCertificate doHandshake() throws IOException, CertificateException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException, TLSHeaderException, HandshakeHeaderException {

        /* client hello */
        this.out.write(new ClientHello(TLSVersion.TLS_11.getStringVersion(), TLSCipherSuites.test).toByte());
        int readBytes = this.readAllAvailable();
        System.out.println(readBytes);
        TLSUtil.printHexByte(buffer, readBytes);

        /* server hello */
        ServerHello serverHello = new ServerHello(buffer);
        System.out.println(serverHello);

        /* certificate */
        CertificateMessage certificateMessage = new CertificateMessage(buffer, serverHello.endOfServerHello());
        System.out.println(certificateMessage);

        return this.byteArrayToX509Certificate(certificateMessage.getCertificates());
    }

    public HostCertificate doMailHandshake(StartTLS start) throws IOException, StartTLSException, TLSHeaderException, HandshakeHeaderException, CertificateException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        /* Start mail handshake */
        if (!this.startMailHandshake(start)) {
            throw new StartTLSException();
        }

        return this.doHandshake();
    }

    public ScanTLSVersion checkTLSVersions(StartTLS start) throws IOException, StartTLSException {
        ScanTLSVersion scanTLSVersion = new ScanTLSVersion();
        InetAddress address = this.socket.getInetAddress();
        ServerHello serverHello;
        int port = this.socket.getPort();

        for (TLSVersion tls : TLSVersion.values()) {
            /* Open and setting the connection */
            this.newConnection(address, port);

            this.readFirstLine();
            this.startMailHandshake(start);

            this.out.write(new ClientHello(tls.getStringVersion(), TLSCipherSuites.test).toByte());

            try {
                this.in.read(buffer);
                serverHello = new ServerHello(buffer);
            } catch (TLSHeaderException | HandshakeHeaderException | SocketTimeoutException e) {
                scanTLSVersion.setTLSVersion(tls, false);
                continue;
            }

            if (Arrays.equals(tls.getByteVersion(), serverHello.getProtocolVersion())) {
                scanTLSVersion.setTLSVersion(tls, true);
            } else {
                scanTLSVersion.setTLSVersion(tls,false);
            }
        }

        return scanTLSVersion;
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
        this.socket = new Socket(address, port);
        this.in = socket.getInputStream();
        this.out =  new DataOutputStream(socket.getOutputStream());
    }

    private boolean startMailHandshake(StartTLS start) throws IOException {
        this.out.write(start.getMessage().getBytes());
        int readBytes = this.in.read(this.buffer);
        String responce = new String(this.buffer,0,readBytes);

        return responce.contains(start.getResponce());
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

    private HostCertificate byteArrayToX509Certificate(List<byte[]> list) throws CertificateException, InvalidAlgorithmParameterException, NoSuchAlgorithmException, NoSuchProviderException {
        X509Certificate[] certs = new X509Certificate[list.size()];
        CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
        int i = 0;

        for (byte[] array : list) {
            InputStream in = new ByteArrayInputStream(array);
            certs[i] = (X509Certificate)certificateFactory.generateCertificate(in);
            ++i;
        }

        X509Certificate[] chain;
        if (i == 1) {
            chain = null;
        } else {
            chain = Arrays.copyOfRange(certs, 1, certs.length);
        }

        return new HostCertificate(certs[0], this.socket.getInetAddress().toString(), validateKeyChain(certs[0], chain), chain);
    }

    public static void main(String[] args) {

//        SMTP smtp =  new SMTP("64.64.18.121");
//        SMTPData data = new SMTPData("64.64.18.121", smtp.startSMTP(), smtp.sendHELP(), smtp.sendEHLO());
//
//        TLS tls = new TLS(smtp.getSocket());
//
//        X509Certificate[] x509Certificates = tls.doMailHandshake(StartTLS.SMTP);
//
//        System.out.println(validateKeyChain(x509Certificates[0], Arrays.copyOfRange(x509Certificates, 1, x509Certificates.length)));

    }
}
