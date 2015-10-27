package cl.tesis.mail;


import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public class POP3 extends Mail{

    private static final int DEFAULT_PORT = 110;

    public POP3(String host, int port) throws IOException {
        super(host, port);
    }

    public POP3(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }

    public static void main(String[] args) throws IOException, HandshakeException, StartTLSException {
        POP3 pop3 =  new POP3("64.64.18.121");
        POP3Data data = new POP3Data("64.64.18.121");
        data.setStart(pop3.startProtocol());

        TLS tls = new TLS(pop3.getSocket());
        data.setCertificate(tls.doProtocolHandshake(StartTLS.POP3));

        System.out.println(data.toJson());
    }

}
