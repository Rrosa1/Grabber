package cl.tesis.http;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpThread extends Thread {
    private static final Logger logger = Logger.getLogger(HttpThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;

    public HttpThread(FileReader reader, FileWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            try {
                Http connection = new Http(columns[IP]);
                HttpData response = new HttpData(columns[IP], connection.getHeader() , connection.getIndex());
                this.writer.writeLine(response);
            } catch (IOException e) {
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            }
        }
    }
}
