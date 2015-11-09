package cl.tesis.mail;


import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.StartTLSException;

import java.io.IOException;

public class POP3 extends Mail{

    private static final int DEFAULT_PORT = 110;

    public POP3(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public POP3(String host) throws IOException, ConnectionException {
        this(host, DEFAULT_PORT);
    }

    public static void main(String[] args) throws IOException, HandshakeException, StartTLSException, ConnectionException {
        POP3 pop3 =  new POP3("64.64.18.121");
        POP3Data data = new POP3Data("64.64.18.121");
        data.setBanner(pop3.readBanner());

        TLS tls = new TLS(pop3.getSocket());
        data.setCertificate(tls.doProtocolHandshake(StartTLS.POP3));

        System.out.println(data.toJson());
    }

}
