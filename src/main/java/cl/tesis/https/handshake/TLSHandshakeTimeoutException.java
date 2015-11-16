package cl.tesis.https.handshake;


public class TLSHandshakeTimeoutException extends Throwable {

    public TLSHandshakeTimeoutException() {
    }

    public TLSHandshakeTimeoutException(String message) {
        super(message);
    }

}
