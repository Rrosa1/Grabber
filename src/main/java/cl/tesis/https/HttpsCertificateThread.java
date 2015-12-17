package cl.tesis.https;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.*;
import cl.tesis.tls.exception.SocketTLSHandshakeException;
import cl.tesis.tls.exception.TLSConnectionException;
import cl.tesis.tls.exception.TLSGetCertificateException;
import cl.tesis.tls.exception.TLSHandshakeException;

import java.security.cert.X509Certificate;
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
            TLSHandshake tls =  null;
            try {
                /* TLS Handshake*/
                tls =  new TLSHandshake(columns[IP], this.port);
                tls.connect();
                X509Certificate[] certs = tls.getChainCertificate();

                data.setTLSProtocol(tls.getProtocol());
                data.setCipherSuite(tls.getCipherSuite());
                data.setChain(Certificate.parseCertificateChain(certs));

                /* Close Connections*/
                tls.close();

                /* Check all SSL/TLS Protocols*/
                if (allProtocols) {
                    ScanTLSProtocols scan = new ScanTLSProtocols(columns[IP], port);
                    data.setProtocols(scan.scanAllProtocols());
                }

                /* Check all Cipher Suites and some vulnerabilities */
                if (allCiphersSuites) {
                    ScanCipherSuites cipherSuites = new ScanCipherSuites(columns[IP], port);
                    data.setCiphersSuites(cipherSuites.scanAllCipherSuites());
                }

                /* Heartbleed */
                if (heartbleed) {
                    ScanHeartbleed scanHeartbleed = new ScanHeartbleed(columns[IP], port);
                    data.setHeartbleed(scanHeartbleed.hasHeartbleed());
                }



            } catch (SocketTLSHandshakeException e) {
                data.setError("Create socket Error");
                logger.log(Level.INFO, "Create socket error {0}", columns[IP]);
            } catch (TLSConnectionException e) {
                data.setError("Connection error");
                logger.log(Level.INFO, "Connection error {0}", columns[IP]);
            } catch (TLSHandshakeException e) {
                data.setError("Handshake error");
                logger.log(Level.INFO, "Handshake error {0}", columns[IP]);
            } catch (TLSGetCertificateException e) {
                data.setError("Certificate error");
                logger.log(Level.INFO, "Certificate error {0}", columns[IP]);
            } finally {
                if (tls !=null)
                    tls.close();
            }

            this.writer.writeLine(data);
        }
    }
}
