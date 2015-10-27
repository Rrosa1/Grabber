package cl.tesis.mail;


import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.HandshakeHeaderException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.exception.TLSHeaderException;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IMAPThread extends Thread{
    private static final Logger logger = Logger.getLogger(IMAPThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;

    public IMAPThread(FileReader reader, FileWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            IMAPData data = new IMAPData(columns[IP]);
            try {
                /* Previous data */
                IMAP imap = new IMAP(columns[IP]);
                data.setStart(imap.startProtocol());

                /* TLS Handshake */
                TLS tls =  new TLS(imap.getSocket());
                data.setCertificate(tls.doProtocolHandshake(StartTLS.IMAP));

            } catch (StartTLSException | HandshakeException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Handshake error {0}", columns[IP]);
            } catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "Read or write over socket error {0}", columns[IP]);
            }

            this.writer.writeLine(data);
        }
    }
}
