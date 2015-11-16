package cl.tesis.mail;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Mail {
    public static final int TIMEOUT = 30000;
    public static final int BUFFER_SIZE = 2048;

    protected String host;
    protected Socket socket;
    protected InputStream in;
    protected DataOutputStream out;
    protected byte[] buffer;

    public Mail(String host, int port) throws IOException {
        this.host = host;
        this.socket = new Socket(host, port);
        this.in = socket.getInputStream();
        this.out = new DataOutputStream(socket.getOutputStream());
        this.buffer =  new byte[BUFFER_SIZE];

        this.socket.setSoTimeout(TIMEOUT); // TODO Change timeout
    }

    public String startProtocol() throws IOException {
        int readBytes = in.read(buffer);
        if (readBytes <= 0)
            return null;

        return new String(buffer, 0, readBytes);
    }

    public String getHost() {
        return host;
    }

    public Socket getSocket() {
        return socket;
    }
}
