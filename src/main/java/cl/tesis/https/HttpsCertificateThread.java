package cl.tesis.https;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;
import tlsNew.Certificate;
import tlsNew.ScanCipherSuites;
import tlsNew.ScanTLSProtocols;
import tlsNew.TLSHandshake;
import tlsNew.exception.SocketTLSHandshakeException;
import tlsNew.exception.TLSConnectionException;
import tlsNew.exception.TLSGetCertificateException;
import tlsNew.exception.TLSHandshakeException;

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
            try {
                /* TLS Handshake*/
                TLSHandshake tls =  new TLSHandshake(columns[IP], this.port);
                tls.connect();
                X509Certificate[] certs = tls.getChainCertificate();

                data.setTLSProtocol(tls.getProtocol());
                data.setCipherSuite(tls.getCipherSuite());
                data.setChain(Certificate.parseCertificateChain(certs));

                /* Check all SSL/TLS Protocols*/
                if (allProtocols) {
                    ScanTLSProtocols scan = new ScanTLSProtocols(columns[IP], port);
                    data.setProtocols(scan.scanAllProtocols());
                }

                /* Check all Cipher Suites */
                if (allCiphersSuites) {
                    ScanCipherSuites cipherSuites = new ScanCipherSuites(columns[IP], port);
                    data.setCiphersSuites(cipherSuites.scanAllCipherSuites());
                }

//                /* Heartbleed test*/
//                if (heartbleed)
//                    data.setHeartbleed(tls.heartbleedTest(null, TLSVersion.TLS_12));

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
            }

            this.writer.writeLine(data);
        }
    }
}
