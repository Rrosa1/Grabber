package cl.tesis.ssh;


import cl.tesis.input.FileReader;
import cl.tesis.output.FileWriter;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SshThread extends Thread {

    private static final Logger logger = Logger.getLogger(SshThread.class.getName());
    private static final int IP = 0;
    private static final int PORT = 22;

    private FileReader reader;
    private FileWriter writer;

    public SshThread(FileReader reader, FileWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            try {
                SshConnection connection = new SshConnection(columns[IP], PORT);
                SshHost host = new SshHost(columns[IP], connection.getSSHVersion());
                this.writer.writeLine(host);
                connection.close();
            } catch (IOException e) {
                logger.log(Level.INFO, "IOException {0}", columns[IP]);
            }
        }
    }
}
