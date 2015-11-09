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

public class POP3Thread extends Thread{
    private static final Logger logger = Logger.getLogger(POP3Thread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;
    private boolean needStartTLS;
    private StartTLS startTLS;
    private boolean allProtocols;
    private boolean allCiphersSuites;
    private boolean heartbleed;

    public POP3Thread(FileReader reader, FileWriter writer, int port, boolean startTLS, boolean allProtocols, boolean allCiphersSuites, boolean heartbleed) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
        this.needStartTLS = startTLS;
        this.allProtocols = allProtocols;
        this.allCiphersSuites = allCiphersSuites;
        this.heartbleed = heartbleed;

        if (needStartTLS) {
            this.startTLS = StartTLS.POP3;
        }
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            POP3Data data =  new POP3Data(columns[IP]);
            try {
                POP3 pop3 = new POP3(columns[IP], this.port);

                /* TLS Handshake */
                TLS tls =  new TLS(pop3.getSocket());
                if (needStartTLS) {
                    data.setBanner(pop3.readBanner());
                    data.setCertificate(tls.doProtocolHandshake(this.startTLS));
                } else {
                    data.setCertificate(tls.doHandshake());
                }

                /* Check all SSL/TLS Protocols */
                if (allProtocols) {
                    data.setProtocols(tls.checkTLSVersions(this.startTLS));
                }

                /* Check all Cipher Suites */
                if (allCiphersSuites) {
                    data.setCiphersSuites(tls.checkCipherSuites(this.startTLS));
                }

                /* Heartbleed test */
                if (heartbleed) {
                    data.setHeartbleed(tls.heartbleedTest(this.startTLS, TLSVersion.TLS_12));
                }

            } catch (StartTLSException | HandshakeException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Handshake error {0}", columns[IP]);
            }  catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "Read or write over socket error {0}", columns[IP]);
            } catch (ConnectionException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Connection Exception {0},  {1}", new String[]{columns[IP], e.getMessage()});
            }

            this.writer.writeLine(data);
        }
    }
}
