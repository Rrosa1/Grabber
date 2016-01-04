package cl.tesis.database;

import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;
import cl.tesis.ssh.SSHConnection;
import cl.tesis.ssh.SSHData;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class MySQLThread extends  Thread{
    private static final Logger logger = Logger.getLogger(MySQLThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;

    public MySQLThread(FileReader reader, FileWriter writer, int port) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            MySQLData data = new MySQLData(columns[IP]);
            try {
                MySQL connection = new MySQL(columns[IP], this.port);
                connection.close();
            } catch (IOException e) {
                data.setError("Read or write socket error");
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            }
            this.writer.writeLine(data);
        }
    }
}
