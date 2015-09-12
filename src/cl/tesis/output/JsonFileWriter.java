package cl.tesis.output;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonFileWriter implements FileWriter{

    private static final Logger logger = Logger.getLogger(JsonFileWriter.class.getName());

    private String fileName;
    private BufferedWriter writer;

    public JsonFileWriter(String fileName) {
        this.fileName = fileName;

        try {
            this.writer =  new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName), "utf-8"));
        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "File not found {0}", this.fileName);
            System.exit(0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public synchronized void writeLine(Writable writable) throws IOException {
        this.writer.write(((JsonWritable)writable).toJson());
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
