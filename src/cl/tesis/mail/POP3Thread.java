package cl.tesis.mail;


import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.TLS;
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

public class POP3Thread extends Thread{
    private static final Logger logger = Logger.getLogger(POP3Thread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;

    public POP3Thread(FileReader reader, FileWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            try {
                POP3 pop3 = new POP3(columns[IP]);
                POP3Data data =  new POP3Data(columns[IP], pop3.startProtocol());
                TLS tls =  new TLS(pop3.getSocket());
                data.setCertificate(tls.doMailHandshake(StartTLS.POP3));
                this.writer.writeLine(data);
            } catch (CertificateException | NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
                logger.log(Level.INFO, "Problem to validate the certificate {0}", columns[IP]);
            } catch (StartTLSException | TLSHeaderException | HandshakeHeaderException e) {
                logger.log(Level.INFO, "Handshake error {0}", columns[IP]);
            } catch (SocketTimeoutException e){
                logger.log(Level.INFO, "Handshake timeout {0}", columns[IP]);
            }catch (IOException e) {
                logger.log(Level.INFO, "Read or write error {0}", columns[IP]);
            }
        }
    }
}
