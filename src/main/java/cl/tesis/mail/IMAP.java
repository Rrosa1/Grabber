package cl.tesis.mail;


import java.io.IOException;

public class IMAP extends Mail{

    private static final int DEFAULT_PORT = 143;

    public IMAP(String host, int port) throws IOException {
        super(host, port);
    }

    public IMAP(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }

}
