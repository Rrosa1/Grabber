package cl.tesis.mail;


import cl.tesis.input.FileReader;
import cl.tesis.mail.exception.ConnectionException;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.Certificate;
import cl.tesis.tls.ScanCipherSuites;
import cl.tesis.tls.ScanTLSProtocols;
import cl.tesis.tls.TLSHandshake;
import cl.tesis.tls.exception.*;

import java.security.cert.X509Certificate;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IMAPThread extends Thread{
    private static final Logger logger = Logger.getLogger(IMAPThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;
    private boolean needStartTLS;
    private StartTLS startTLS;
    private boolean allProtocols;
    private boolean allCiphersSuites;
    private boolean heartbleed;


    public IMAPThread(FileReader reader, FileWriter writer, int port, boolean startTLS, boolean allProtocols, boolean allCiphersSuites, boolean heartbleed) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
        this.needStartTLS = startTLS;
        this.allProtocols = allProtocols;
        this.allCiphersSuites = allCiphersSuites;
        this.heartbleed = heartbleed;

        if (needStartTLS) {
            this.startTLS = StartTLS.IMAP;
        }
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            IMAPData data = new IMAPData(columns[IP]);
            IMAP imap = null;
            TLSHandshake tlsHandshake = null;

            try {
                if (needStartTLS) { // STARTTLS

                    /* IMAP StartTLS */
                    imap = new IMAP(columns[IP], this.port);
                    data.setBanner(imap.readBanner());

                    tlsHandshake = new TLSHandshake(imap, this.startTLS);
                    tlsHandshake.connect();
                    X509Certificate[] certs = tlsHandshake.getChainCertificate();
                    data.setChain(Certificate.parseCertificateChain(certs));

                    /* Close Connection */
                    imap.close();
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

                } else { // Secure Port

                    /* Handshake */
                    tlsHandshake = new TLSHandshake(columns[IP], port);
                    tlsHandshake.connect();
                    X509Certificate[] certs = tlsHandshake.getChainCertificate();
                    data.setChain(Certificate.parseCertificateChain(certs));

                    /* Close Connection */
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
                if (needStartTLS && imap != null)
                    imap.close();
                if (tlsHandshake !=null)
                    tlsHandshake.close();
            }

            this.writer.writeLine(data);
        }
    }
}
