package cl.tesis.mail;


import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.StartTLSException;

import java.io.IOException;

public class IMAP extends Mail{

    private static final int DEFAULT_PORT = 143;

    public IMAP(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public IMAP(String host) throws ConnectionException {
        this(host, DEFAULT_PORT);
    }

    public static void main(String[] args) throws IOException, HandshakeException, StartTLSException, ConnectionException {
        IMAP imap = new IMAP("210.79.49.210");
        IMAPData data = new IMAPData("210.79.49.210");
        data.setStart(imap.readBanner());

        TLS tls = new TLS(imap.getSocket());
        data.setCertificate(tls.doProtocolHandshake(StartTLS.IMAP));

        System.out.println(data.toJson());

    }


}
