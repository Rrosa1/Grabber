package cl.tesis.mail;

import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.*;
import java.net.Socket;

public class SMTP {

    public static final String STARTTLS = "STARTTLS\r\n";

    private static final int DEFAULT_PORT = 25;
    private static final String HELP = "HELP\r\n";
    private static final String EHLO = "EHLO example.cl\r\n";

    private String host;
    private Socket socket;
    private InputStream in;
    private DataOutputStream out;

    public SMTP(String host, int port) throws IOException {
        this.host = host;
        this.socket = new Socket(host, port);
        this.in = socket.getInputStream();
        this.out = new DataOutputStream(socket.getOutputStream());

//  TODO set timeout
    }

    public SMTP(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }

    public String startSMTP() throws IOException {
        byte[] buffer =  new byte[1024];

        int readBytes = in.read(buffer);

        return new String(buffer, 0, readBytes);
    }

    public String sendHELP() throws IOException {
        byte[] buffer = new byte[2048];

        this.out.write(HELP.getBytes());
        int readBytes = in.read(buffer);

        return new String(buffer, 0, readBytes);
    }

    public String sendEHLO() throws IOException {
        byte[] buffer = new byte[2048];

        this.out.write(EHLO.getBytes());
        int readBytes =  in.read(buffer);

        return new String(buffer, 0, readBytes);
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

    public static void main(String[] args) throws IOException, StartTLSException, HandshakeHeaderException, TLSHeaderException {
        SMTP smtp =  new SMTP("192.80.24.2");
        SMTPData data = new SMTPData("192.80.24.2", smtp.startSMTP(), smtp.sendHELP(), smtp.sendEHLO());

        TLS tls = new TLS(smtp.getSocket(), smtp.getIn(), smtp.getOut(), SMTP.STARTTLS);
        tls.doHandshake();
    }

}
