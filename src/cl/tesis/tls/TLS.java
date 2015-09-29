package cl.tesis.tls;

import cl.tesis.tls.exception.StartTLSException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class TLS {

    private static final int BUFFER_SIZE = 4098;


    private Socket socket;
    private InputStream in;
    private DataOutputStream out;
    private String startTLS;
    private byte[] buffer;

    public TLS(Socket socket, InputStream in, DataOutputStream out, String startTLS) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        this.startTLS = startTLS;
        this.buffer = new byte[BUFFER_SIZE];
    }


    public void doHandshake() throws IOException, StartTLSException {

        /* start handshake */
        if (!this.startHandshake()) {
            throw new StartTLSException();
        }

        /* client hello */
        this.out.write(new ClientHello(TLSVersion.TLS_11, TLSCipherSuites.test).toByte());
        int readBytes = this.in.read(buffer);
        TLSUtil.printHexByte(buffer, readBytes);

        /* server hello */
        ServerHello serverHello = new ServerHello(buffer);
        System.out.println(serverHello);

        /* certificate */
        CertificateMessage certificateMessage = new CertificateMessage(buffer, serverHello.endOfServerHello());
        System.out.println(certificateMessage);
    }

//  TODO handle the errors
    public boolean startHandshake() throws IOException {
        this.out.write(this.startTLS.getBytes());
        int readBytes = this.in.read(buffer);
        String responce = new String(buffer,0,readBytes);

        return responce.contains("220");
    }
}
