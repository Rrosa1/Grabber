package cl.tesis.mail;


import cl.tesis.mail.exception.ConnectionException;

import java.io.Closeable;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class StartTLSProtocol implements Closeable{

    protected static final int MILLISECONDS = 1000;
    protected static final int TIMEOUT = 30 * MILLISECONDS; // 30 Seg
    protected static final int BUFFER_SIZE = 2048;

    protected String host;
    protected Socket socket;
    protected InputStream in;
    protected DataOutputStream out;
    protected byte[] buffer;

    public StartTLSProtocol(String host, int port) throws ConnectionException {
        try {
            this.host = host;
            this.socket = new Socket(host, port);
            this.socket.setSoTimeout(TIMEOUT);

            this.in = socket.getInputStream();
            this.out = new DataOutputStream(socket.getOutputStream());
            this.buffer = new byte[BUFFER_SIZE];

        } catch (IOException e) {
            e.printStackTrace();
            throw new ConnectionException("Can't create the connection");
        }
    }

    public String getHost() {
        return host;
    }

    public Socket getSocket() {
        return socket;
    }

    public InputStream getIn() {
        return in;
    }

    public DataOutputStream getOut() {
        return out;
    }

    public byte[] getBuffer() {
        return buffer;
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

    @Override
    public void close() {
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
