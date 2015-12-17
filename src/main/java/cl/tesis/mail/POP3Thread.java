package cl.tesis.mail;


import cl.tesis.input.FileReader;
import cl.tesis.mail.exception.ConnectionException;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.*;
import cl.tesis.tls.exception.*;

import java.security.cert.X509Certificate;
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
            POP3 pop = null;
            TLSHandshake tlsHandshake = null;

            try {
                if (needStartTLS) { // STARTTLS

                    /* POP3 StartTLS */
                    pop = new POP3(columns[IP], this.port);
                    data.setBanner(pop.readBanner());

                    /* Handshake */
                    tlsHandshake = new TLSHandshake(pop, this.startTLS);
                    tlsHandshake.connect();
                    X509Certificate[] certs = tlsHandshake.getChainCertificate();
                    data.setChain(Certificate.parseCertificateChain(certs));

                    /* Close Connections */
                    pop.close();
                    tlsHandshake.close();

                    /* Check all SSL/TLS Protocols*/
                    if (allProtocols) {
                        ScanTLSProtocols protocols = new ScanTLSProtocols(columns[IP], port);
                        data.setProtocols(protocols.scanAllProtocols(this.startTLS));
                    }

                    /* Check all Cipher Suites */
                    if (allCiphersSuites) {
                        ScanCipherSuites cipherSuites = new ScanCipherSuites(columns[IP], port);
                        data.setCiphersSuites(cipherSuites.scanAllCipherSuites(this.startTLS));
                    }

                    /* Heartbleed */
                    if (heartbleed) {
                        ScanHeartbleed scanHeartbleed = new ScanHeartbleed(columns[IP], port);
                        data.setHeartbleed(scanHeartbleed.hasHeartbleed(this.startTLS));
                    }

                } else { // Secure Port

                    /* Handshake */
                    tlsHandshake = new TLSHandshake(columns[IP], port);
                    tlsHandshake.connect();
                    X509Certificate[] certs = tlsHandshake.getChainCertificate();
                    data.setChain(Certificate.parseCertificateChain(certs));

                    /* Close Connections */
                    tlsHandshake.close();

                    /* Check all SSL/TLS Protocols*/
                    if (allProtocols) {
                        ScanTLSProtocols protocols = new ScanTLSProtocols(columns[IP], port);
                        data.setProtocols(protocols.scanAllProtocols());
                    }

                    /* Check all Cipher Suites */
                    if (allCiphersSuites) {
                        ScanCipherSuites cipherSuites = new ScanCipherSuites(columns[IP], port);
                        data.setCiphersSuites(cipherSuites.scanAllCipherSuites());
                    }

                    /* Heartbleed */
                    if (heartbleed) {
                        ScanHeartbleed scanHeartbleed = new ScanHeartbleed(columns[IP], port);
                        data.setHeartbleed(scanHeartbleed.hasHeartbleed());
                    }

                }
            } catch (ConnectionException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Connection Exception {0},  {1}", new String[]{columns[IP], e.getMessage()});
            } catch (SocketTLSHandshakeException | TLSConnectionException e) {
                data.setError("Connection error");
                logger.log(Level.INFO, "Connection error {0}", columns[IP]);
            } catch (StartTLSException e) {
                data.setError("Start Protocol error");
                logger.log(Level.INFO, "Start Protocol error {0}", columns[IP]);
            }  catch (TLSHandshakeException e) {
                data.setError("Handshake error");
                logger.log(Level.INFO, "Handshake error {0}", columns[IP]);
            } catch (TLSGetCertificateException e) {
                data.setError("Certificate get error");
                logger.log(Level.INFO, "Certificate get error {0}", columns[IP]);
            } finally {
                if (needStartTLS && pop != null)
                    pop.close();
                if (tlsHandshake !=null)
                    tlsHandshake.close();
            }

            this.writer.writeLine(data);
        }
    }
}
