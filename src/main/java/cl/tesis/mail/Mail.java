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

    public Mail(String host, int port) throws ConnectionException{
        try {
            this.host = host;
            this.socket = new Socket(host, port);
            this.in = socket.getInputStream();
            this.out = new DataOutputStream(socket.getOutputStream());
            this.buffer = new byte[BUFFER_SIZE];

            this.socket.setSoTimeout(TIMEOUT); // TODO Change timeout
        } catch (IOException e) {
            throw new ConnectionException("Can't create the connection");
        }
    }

    public String readBanner() throws ConnectionException {
        try {
            int readBytes = in.read(buffer);
            if (readBytes <= 0)
                return null;

            return new String(buffer, 0, readBytes);
        } catch (IOException e) {
            throw new ConnectionException("Can't read the protocol banner");
        }
    }

    public String getHost() {
        return host;
    }

    public Socket getSocket() {
        return socket;
    }
}
