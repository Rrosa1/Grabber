package cl.tesis.mail;


import cl.tesis.mail.exception.ConnectionException;

public class IMAP extends Mail{

    private static final int DEFAULT_PORT = 143;

    public IMAP(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public IMAP(String host) throws ConnectionException {
        this(host, DEFAULT_PORT);
    }

}
