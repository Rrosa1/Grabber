package cl.tesis.tls.exception;

/**
 * Created by eduardo on 26-10-15.
 */
public class HandshakeException extends TLSException{

    public HandshakeException() {}

    public HandshakeException(String message) {
        super(message);
    }

}
