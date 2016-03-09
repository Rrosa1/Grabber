package cl.tesis.database;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostgreSQLThread extends Thread {
    private static final Logger logger = Logger.getLogger(PostgreSQLThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;

    public PostgreSQLThread(FileReader reader, FileWriter writer, int port) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            PostgreSQLData data = new PostgreSQLData(columns[IP]);
            try {
                PostgreSQL connection = new PostgreSQL(columns[IP], this.port);
                // TODO save the get version value using set response method
                connection.close();
            } catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            }
            this.writer.writeLine(data);
        }
    }
}

