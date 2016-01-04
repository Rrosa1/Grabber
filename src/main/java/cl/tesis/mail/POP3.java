package cl.tesis.mail;


import cl.tesis.mail.exception.ConnectionException;

import java.io.IOException;

public class POP3 extends Mail{

    private static final int DEFAULT_PORT = 110;

    public POP3(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public POP3(String host) throws IOException, ConnectionException {
        this(host, DEFAULT_PORT);
    }
}
