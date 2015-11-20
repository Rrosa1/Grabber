package cl.tesis.mail;

import cl.tesis.mail.exception.ConnectionException;


public class Mail extends StartTLSProtocol{

    public Mail(String host, int port) throws ConnectionException {
        super(host, port);
    }

}
