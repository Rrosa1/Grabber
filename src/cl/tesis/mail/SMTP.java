package cl.tesis.mail;

import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public class SMTP extends Mail {

    private static final int DEFAULT_PORT = 25;
    private static final String HELP = "HELP\r\n";
    private static final String EHLO = "EHLO example.cl\r\n";

    public SMTP(String host, int port) throws IOException {
        super(host, port);
    }

    public SMTP(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }

    public String sendHELP() throws IOException {
        this.out.write(HELP.getBytes());
        int readBytes = in.read(buffer);
        if (readBytes <= 0)
            return null;

        return new String(buffer, 0, readBytes);
    }

    public String sendEHLO() throws IOException {
        this.out.write(EHLO.getBytes());
        int readBytes =  in.read(buffer);
        if (readBytes <= 0)
            return null;

        return new String(buffer, 0, readBytes);
    }

    public static void main(String[] args) throws IOException, HandshakeException, StartTLSException {
        SMTP smtp =  new SMTP("192.80.24.2");
        SMTPData data = new SMTPData("192.80.24.2", smtp.startProtocol(), smtp.sendHELP(), smtp.sendEHLO());

        TLS tls = new TLS(smtp.getSocket());
        data.setCertificate(tls.doProtocolHandshake(StartTLS.SMTP));

        System.out.println(data.toJson());
    }

}
