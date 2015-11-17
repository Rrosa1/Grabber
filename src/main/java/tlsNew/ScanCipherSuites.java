package tlsNew;


import cl.tesis.mail.SMTP;
import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;
import cl.tesis.tls.handshake.*;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class ScanCipherSuites {
    private static final int MILLISECONDS = 1000;
    private static final int TIMEOUT = 60 * MILLISECONDS; // 60 seg
    private static final int BUFFER_SIZE = 2048;

    private String host;
    private int port;
    private byte[] buffer;

    private Socket socket;
    private InputStream in;
    private DataOutputStream out;

    public ScanCipherSuites(String host, int port) {
        this.host = host;
        this.port = port;
        this.buffer =  new byte[BUFFER_SIZE];
    }

    public ScanCipherSuitesData scanAllCipherSuites() {
        ScanCipherSuitesData scanCipherSuitesData = new ScanCipherSuitesData();
        for (TLSCipher cipher : TLSCipher.values()) {
            scanCipherSuitesData.setCipherSuite(cipher, scanCipherSuite(cipher, null));
        }
        return scanCipherSuitesData;
    }

    public ScanCipherSuitesData scanAllCipherSuites(StartTLS start) {
        ScanCipherSuitesData scanCipherSuitesData = new ScanCipherSuitesData();
        for (TLSCipher cipher : TLSCipher.values()) {
            scanCipherSuitesData.setCipherSuite(cipher, scanCipherSuite(cipher, start));
        }
        return scanCipherSuitesData;
    }

    private String scanCipherSuite(TLSCipher cipher, StartTLS start) {
        ServerHello serverHello;
        try {
            /* Open and setting the connection */
            this.getConnection();

                /* STARTTLS */
            if (start != null) {
                this.ignoreFirstLine();
                this.startProtocolHandshake(start);
            }

            this.out.write(new ClientHello(TLSVersion.TLS_11.getStringVersion(), cipher).toByte());
            this.in.read(buffer);
            serverHello = new ServerHello(buffer);

        } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException | IOException e) {
            this.close();
            return null;
        }

        this.close();
        return CipherSuites.getNameByByte(serverHello.getCipherSuite());
    }

    private void getConnection() throws IOException {
        this.socket = new Socket(host, port);
        this.socket.setSoTimeout(TIMEOUT);
        this.in = socket.getInputStream();
        this.out =  new DataOutputStream(socket.getOutputStream());
    }

    private void ignoreFirstLine() throws IOException {
        in.read(this.buffer);
    }

    private void startProtocolHandshake(StartTLS start) throws StartTLSException {
        String response;
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

    private void close() {
        if (in != null)
            try{
                in.close();
            } catch (IOException ignore) {
            } finally { in = null; }

        if (out != null)
            try{
                out.close();
            } catch (IOException ignore) {
            } finally { out = null; }

        if (socket != null)
            try{
                socket.close();
            } catch (IOException ignore) {
            } finally { socket = null; }
    }

}
