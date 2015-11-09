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
    private int port;

    public HttpThread(FileReader reader, FileWriter writer, int port) {
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
                Http connection = new Http(columns[IP], this.port);
                data.setHeader(connection.getHeader());
                data.setIndex(connection.getIndex());
            } catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            }
            this.writer.writeLine(data);
            data.clear();
        }
    }
}
