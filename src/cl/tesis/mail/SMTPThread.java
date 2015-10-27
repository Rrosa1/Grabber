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

public class SMTPThread extends Thread{
    private static final Logger logger = Logger.getLogger(SMTPThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private boolean allProtocols;
    private boolean allCiphersSuites;

    public SMTPThread(FileReader reader, FileWriter writer, boolean allProtocols, boolean allCiphersSuites) {
        this.reader = reader;
        this.writer = writer;
        this.allProtocols = allProtocols;
        this.allCiphersSuites = allCiphersSuites;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            SMTPData data =  new SMTPData(columns[IP]);
            try {
                SMTP smtp = new SMTP(columns[IP]);

                /* Previous data */
                data.setStart(smtp.startProtocol());
                data.setHelp(smtp.sendHELP());
                data.setEhlo(smtp.sendEHLO());

                /* TLS Handshake*/
                TLS tls =  new TLS(smtp.getSocket());
                data.setCertificate(tls.doProtocolHandshake(StartTLS.SMTP));

                /* Check all SSL/TLS Protocols*/
                if (allProtocols)
                    data.setProtocols(tls.checkTLSVersions(StartTLS.SMTP));

                /* Check all Cipher Suites */
                if (allCiphersSuites)
                    data.setCiphersSuites(tls.checkCipherSuites(StartTLS.SMTP));

                /* */

            } catch (StartTLSException | HandshakeException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Handshake error {0}", columns[IP]);
            }  catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "Read or write over socket error {0}", columns[IP]);
            }

            this.writer.writeLine(data);
        }
    }

}
