package cl.tesis.ssh;


import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SSHThread extends Thread {

    private static final Logger logger = Logger.getLogger(SSHThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;

    public SSHThread(FileReader reader, FileWriter writer, int port) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            SSHData host = new SSHData(columns[IP]);
            try {
                SSHConnection connection = new SSHConnection(columns[IP], this.port);
                connection.close();
            } catch (IOException e) {
                host.setError("Read or write socket error");
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            }
            this.writer.writeLine(host);
        }
    }
}
