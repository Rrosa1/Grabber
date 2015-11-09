package cl.tesis.https;

import cl.tesis.http.Http;
import cl.tesis.http.HttpData;
import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HttpsThread extends  Thread{
    private static final Logger logger = Logger.getLogger(HttpsThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;

    public HttpsThread(FileReader reader, FileWriter writer, int port) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
    }

    @Override
    public void run() {
        String[] columns;
        HttpData data = new HttpData();

        while((columns = this.reader.nextLine()) != null) {
            try {
                data.setIp(columns[IP]);
                Https connection = new Https(columns[IP], this.port);
                data.setHeader(connection.getHeader());
                data.setIndex(connection.getIndex());
            } catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            } catch (NoSuchAlgorithmException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "NoSuchAlgorithmException {0}", columns[IP]);
            } catch (KeyManagementException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "KeyManagementException {0}", columns[IP]);
            }
            this.writer.writeLine(data);
            data.clear();
        }
    }
}
