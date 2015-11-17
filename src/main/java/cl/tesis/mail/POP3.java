package cl.tesis.mail;


import java.io.IOException;

public class POP3 extends Mail{

    private static final int DEFAULT_PORT = 110;

    public POP3(String host, int port) throws IOException {
        super(host, port);
    }

    public POP3(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }
}
