package cl.tesis.ssl;

import cl.tesis.output.FileWriter;
import cl.tesis.ssl.exception.SSLConnectionException;
import cl.tesis.ssl.exception.SSLHandshakeTimeoutException;
import cl.tesis.input.FileReader;

import javax.net.ssl.SSLHandshakeException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class SSLCertificateThreads extends  Thread{
    private static final Logger logger = Logger.getLogger(SSLCertificateThreads.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;

    public SSLCertificateThreads(FileReader reader, FileWriter writer) {

        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        String[] columns;

        while ((columns = this.reader.nextLine()) != null) {
            try {
                HostCertificate certificate = SSLUtil.getServerCertificate(columns[IP], true);
                this.writer.writeLine(certificate);
            } catch (SSLHandshakeException e) { // Untrusted HostCertificate
                logger.log(Level.INFO, "Untrusted certificate {0}", columns[IP]);
                try {
                    HostCertificate certificate = SSLUtil.getServerCertificate(columns[IP], false);
                    this.writer.writeLine(certificate);
                } catch (Throwable throwable) {
                    logger.log(Level.INFO, "Other exception {0}", columns[IP]);
                }
            } catch (SSLConnectionException e) { // Problem creating the socket
                logger.log(Level.INFO, "Problem creating the soceket {0}", columns[IP]);
            } catch (SocketTimeoutException e) { // Connection timeout
                logger.log(Level.INFO, "Connection timeout {0}", columns[IP]);
            } catch (SSLHandshakeTimeoutException e) {
                logger.log(Level.INFO, "Handshake timeout {0}", columns[IP]);
            } catch (ConnectException e) { // Problem in the connection
                logger.log(Level.INFO, "Connection exception {0}", columns[IP]);
            } catch (Throwable e) { // Other errors
                logger.log(Level.INFO, "Other exception {0}", columns[IP]);
            }

        }
    }


}
