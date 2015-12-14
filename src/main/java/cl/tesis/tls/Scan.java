package cl.tesis.tls;

import cl.tesis.mail.SMTP;
import cl.tesis.mail.StartTLS;
import cl.tesis.tls.exception.StartTLSException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;


public class Scan {
    protected static final int BUFFER_SIZE = 4096;
    private static final int MILLISECONDS = 1000;
    private static final int TIMEOUT = 60 * MILLISECONDS; // 60 seg

    protected String host;
    protected int port;

    protected byte[] buffer;
    protected InputStream in;
    protected DataOutputStream out;
    protected Socket socket;

    public Scan(String host, int port) {
        this.host = host;
        this.port = port;
        this.buffer =  new byte[BUFFER_SIZE];
    }

    protected void getConnection(String host, int port) throws IOException {
        this.socket = new Socket(this.host, this.port);
        this.socket.setSoTimeout(TIMEOUT);
        this.in = socket.getInputStream();
        this.out =  new DataOutputStream(socket.getOutputStream());
    }

    protected void ignoreFirstLine() throws IOException {
        in.read(this.buffer);
    }

    protected void startProtocolHandshake(StartTLS start) throws StartTLSException {
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

    protected void close() {
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
