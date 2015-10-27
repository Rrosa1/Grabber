package cl.tesis.mail;


import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;

public class IMAP extends Mail{

    private static final int DEFAULT_PORT = 143;

    public IMAP(String host, int port) throws IOException {
        super(host, port);
    }

    public IMAP(String host) throws IOException {
        this(host, DEFAULT_PORT);
    }

    public static void main(String[] args) throws IOException, HandshakeException, StartTLSException {
        IMAP imap = new IMAP("210.79.49.210");
        IMAPData data = new IMAPData("210.79.49.210");
        data.setStart(imap.startProtocol());

        TLS tls = new TLS(imap.getSocket());
        data.setCertificate(tls.doProtocolHandshake(StartTLS.IMAP));

        System.out.println(data.toJson());

    }


}
