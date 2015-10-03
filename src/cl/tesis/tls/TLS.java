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

    private Socket socket;
    private InputStream in;
    private DataOutputStream out;
    private byte[] buffer;

    public TLS(Socket socket) throws IOException {
        this.socket = socket;
        this.socket.setSoTimeout(1000);
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
           /* start handshake */
        if (!this.startHandshake(start)) {
            throw new StartTLSException();
        }

        this.doHandshake();
    }

    public void checkTLSVersions(StartTLS start) throws IOException, StartTLSException {
        InetAddress address = this.socket.getInetAddress();
        int port = this.socket.getPort();

        for (TLSVersion tls : TLSVersion.values()) {
            this.socket = new Socket(address, port);
            this.socket.setSoTimeout(3000);
            this.in = socket.getInputStream();
            this.out =  new DataOutputStream(socket.getOutputStream());

            ServerHello serverHello;

            this.in.read(buffer);
            out.write(start.getMessage().getBytes());
            this.in.read(buffer);

            out.write(new ClientHello(tls.getStringVersion(), TLSCipherSuites.test).toByte());

            try {
                this.readAllAvailable();
                serverHello = new ServerHello(buffer);
            } catch (TLSHeaderException | HandshakeHeaderException e) {
                System.out.println(tls.toString() + " : No");
                continue;
            }

            if (Arrays.equals(tls.getByteVersion(), serverHello.getProtocolVersion())) {
                System.out.println(tls.getName() + " : Yes");
            } else {
                System.out.println(tls.getName() + " : No");
            }
        }
    }

    private boolean startHandshake(StartTLS start) throws IOException {
        this.out.write(start.getMessage().getBytes());
        int readBytes = this.in.read(buffer);
        String responce = new String(buffer,0,readBytes);

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
        Socket s =  new Socket("64.64.18.120", 110);
        TLS tls = new TLS(s);
        tls.checkTLSVersions(StartTLS.POP3);
    }
}
