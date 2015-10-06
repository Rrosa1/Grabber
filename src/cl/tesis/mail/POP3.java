package cl.tesis.mail;


import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

public class POP3 extends Mail{

    private static final int DEFAULT_PORT = 110;

    public POP3(String host, int port) throws IOException {
        super(host, port);
    }

    public POP3(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }

    public static void main(String[] args) throws IOException, StartTLSException, HandshakeHeaderException, TLSHeaderException {
        POP3 pop3 =  new POP3("64.64.18.121");
        System.out.println(pop3.startProtocol());


        TLS tls = new TLS(pop3.getSocket());
        tls.doMailHandshake(StartTLS.POP3);
    }

}
