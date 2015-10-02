package cl.tesis.mail;

public enum StartTLS {
    SMTP("STARTTLS\r\n", "220"),
    POP3("STLS\r\n", "+OK"),
    IMAP("a001 STARTTLS\r\n", "OK");

    private final String message;
    private final String responce;

    StartTLS(String msg, String resp) {
       this.message = msg;
       this.responce = resp;
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
