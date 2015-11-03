package cl.tesis.https;

import cl.tesis.input.FileReader;
import cl.tesis.mail.SMTP;
import cl.tesis.mail.SMTPData;
import cl.tesis.mail.StartTLS;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.TLS;
import cl.tesis.tls.exception.HandshakeException;
import cl.tesis.tls.exception.StartTLSException;
import cl.tesis.tls.handshake.TLSVersion;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpsCertificateThread extends Thread{
    private static final Logger logger = Logger.getLogger(HttpsCertificateThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;
    private boolean allProtocols;
    private boolean allCiphersSuites;
    private boolean heartbleed;

    public HttpsCertificateThread(FileReader reader, FileWriter writer, int port, boolean allProtocols, boolean allCiphersSuites, boolean heartbleed) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
        this.allProtocols = allProtocols;
        this.allCiphersSuites = allCiphersSuites;
        this.heartbleed = heartbleed;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            HttpsCertificateData data =  new HttpsCertificateData(columns[IP]);
            try {
                /* TLS Handshake*/
                TLS tls =  new TLS(new Socket(columns[IP], this.port));
                data.setCertificate(tls.doHandshake());


                /* Check all SSL/TLS Protocols*/
                if (allProtocols)
                    data.setProtocols(tls.checkTLSVersions(null));

                /* Check all Cipher Suites */
                if (allCiphersSuites)
                    data.setCiphersSuites(tls.checkCipherSuites(null));

                /* Heartbleed test*/
                if (heartbleed)
                    data.setHeartbleed(tls.heartbleedTest(null, TLSVersion.TLS_12));

            } catch (HandshakeException e) {
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
