package cl.tesis.script;


import cl.tesis.input.FileReader;
import cl.tesis.mail.exception.ConnectionException;
import cl.tesis.output.FileWriter;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ScriptingThread extends Thread{
    private static final Logger logger  = Logger.getLogger(ScriptingThread.class.getName());
    private static final int IP = 0;

    private FileReader reader;
    private FileWriter writer;
    private int port;
    private ArrayList<String> packets;

    public ScriptingThread(FileReader reader, FileWriter writer, int port, ArrayList<String> packets) {
        this.reader = reader;
        this.writer = writer;
        this.port = port;
        this.packets = packets;

    }

    @Override
    public void run() {
        String[] columns;

        while((columns = this.reader.nextLine()) != null) {
            ScriptingData data = new ScriptingData(columns[IP]);
            Scripting script = null;

            try {

                script =  new Scripting(columns[IP], this.port, this.packets);
                data.setResponse(script.sendPacket());

            } catch (ConnectionException e) {
                data.setError(e.getMessage());
                logger.log(Level.INFO, "Connection Exception {0},  {1}", new String[]{columns[IP], e.getMessage()});
            } catch (Exception e) {
                logger.log(Level.INFO, "Unexpected error {0}, {1}", new String[]{columns[IP], e.getMessage()});
            }
        }
    }
}
