package cl.tesis.mail;

public enum StartTLS {
    SMTP("SMTP", "STARTTLS\r\n", "220"),
    POP3("POP3", "STLS\r\n", "+OK"),
    IMAP("IMAP", "a001 STARTTLS\r\n", "OK");

    private final String protocol;
    private final String message;
    private final String responce;

    StartTLS(String protocol, String message, String responce) {
        this.protocol = protocol;
        this.message = message;
        this.responce = responce;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getMessage() {
        return message;
    }

    public String getResponce() {
        return responce;
    }

    public static void main(String[] args) {
        System.out.println(SMTP.message);
    }
}
