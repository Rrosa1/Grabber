package cl.tesis.ftp;

import cl.tesis.input.FileReader;
import cl.tesis.mail.exception.ConnectionException;
import cl.tesis.output.FileWriter;
import cl.tesis.tls.TLSHandshake;

import java.util.logging.Level;
import java.util.logging.Logger;


public class FTPThread extends Thread{
    private static final Logger logger = Logger.getLogger(FTPThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;

    public FTPThread(FileReader reader, FileWriter writer, int port) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;

    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            FTPData data =  new FTPData(columns[IP]);
            FTP ftp = null;
            TLSHandshake tlsHandshake = null;

            try {
                /* SMTP StarTLS */
                ftp = new FTP(columns[IP], this.port);
                data.setBanner(ftp.readBanner());
            } catch (ConnectionException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Connection Exception {0},  {1}", new String[]{columns[IP], e.getMessage()});
           }

            this.writer.writeLine(data);
        }
    }
}
