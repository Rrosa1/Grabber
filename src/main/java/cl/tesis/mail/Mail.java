package cl.tesis.mail;

import cl.tesis.mail.exception.ConnectionException;

import java.io.IOException;


public class Mail extends StartTLSProtocol{

    public Mail(String host, int port) throws ConnectionException {
        super(host, port);
    }

    public String readBanner() throws ConnectionException {
        try {
            int readBytes = in.read(buffer);
            if (readBytes <= 0)
                return null;

            return new String(buffer, 0, readBytes);
        } catch (IOException e) {
            throw new ConnectionException("Can't read the protocol banner");
        }
    }

}
