package cl.tesis.mail;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.handshake.TLSVersion;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SMTPThread extends Thread{
    private static final Logger logger = Logger.getLogger(SMTPThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;
    private boolean needStartTLS;
    private StartTLS startTLS;
    private boolean allProtocols;
    private boolean allCiphersSuites;
    private boolean heartbleed;

    public SMTPThread(FileReader reader, FileWriter writer, int port, boolean startTLS, boolean allProtocols, boolean allCiphersSuites, boolean heartbleed) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
        this.needStartTLS = startTLS;
        this.allProtocols = allProtocols;
        this.allCiphersSuites = allCiphersSuites;
        this.heartbleed = heartbleed;

        if (needStartTLS) {
            this.startTLS = StartTLS.SMTP;
        }
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            SMTPData data =  new SMTPData(columns[IP]);
            try {
                SMTP smtp = new SMTP(columns[IP], this.port);

                /* TLS Handshake*/
                TLS tls =  new TLS(smtp.getSocket());

                if (needStartTLS) {
                    data.setStart(smtp.startProtocol());
                    data.setHelp(smtp.sendHELP());
                    data.setEhlo(smtp.sendEHLO());
                    data.setCertificate(tls.doProtocolHandshake(this.startTLS));
                } else {
                    data.setCertificate(tls.doHandshake());
                }

                /* Check all SSL/TLS Protocols*/
                if (allProtocols)
                    data.setProtocols(tls.checkTLSVersions(this.startTLS));

                /* Check all Cipher Suites */
                if (allCiphersSuites)
                    data.setCiphersSuites(tls.checkCipherSuites(this.startTLS));

                /* Heartbleed test*/
                if (heartbleed)
                    data.setHeartbleed(tls.heartbleedTest(this.startTLS, TLSVersion.TLS_12));

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
