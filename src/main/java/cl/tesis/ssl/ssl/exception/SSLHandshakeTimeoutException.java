package cl.tesis.ssl.ssl.exception;

public class SSLHandshakeTimeoutException extends Exception {

    public SSLHandshakeTimeoutException() {
    }

    public SSLHandshakeTimeoutException(String message) {
        super(message);
    }

    public SSLHandshakeTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }
}
