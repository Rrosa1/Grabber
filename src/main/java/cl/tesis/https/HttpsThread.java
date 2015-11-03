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

        while((columns = this.reader.nextLine()) != null) {
            HttpData response = new HttpData(columns[IP]);
            try {
                Https connection = new Https(columns[IP], this.port);
                response.setHeader(connection.getHeader());
                response.setIndex(connection.getIndex());
            } catch (IOException e) {
                response.setError("Read or write socket error");
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            } catch (NoSuchAlgorithmException e) {
                response.setError(e.getMessage());
                logger.log(Level.INFO, "NoSuchAlgorithmException {0}", columns[IP]);
            } catch (KeyManagementException e) {
                response.setError(e.getMessage());
                logger.log(Level.INFO, "KeyManagementException {0}", columns[IP]);
            }
            this.writer.writeLine(response);
        }
    }
}
