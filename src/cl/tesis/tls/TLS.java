package cl.tesis.tls;

import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Arrays;

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


    public void doHandshake() throws IOException, TLSHeaderException, HandshakeHeaderException {

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
    }

    public void doMailHandshake(StartTLS start) throws IOException, StartTLSException, TLSHeaderException, HandshakeHeaderException {
        /* Start mail handshake */
        if (!this.startMailHandshake(start)) {
            throw new StartTLSException();
        }

        this.doHandshake();
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
        } catch (SocketTimeoutException e){
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

    public static void main(String[] args) throws IOException, StartTLSException {
        Socket s =  new Socket("64.64.18.121", 110);
        TLS tls = new TLS(s);
        System.out.println(tls.checkTLSVersions(StartTLS.POP3).toJson());
    }
}
