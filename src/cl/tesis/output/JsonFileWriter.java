package cl.tesis.output;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JsonFileWriter implements FileWriter{

    private static final Logger logger = Logger.getLogger(JsonFileWriter.class.getName());
//    private static final String start = "[\n";

    private String fileName;
    private BufferedWriter writer;
    private boolean header;

    public JsonFileWriter(String fileName) {
        this.fileName = fileName;
        this.header = true;

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
        if (this.header){
            this.writer.write("[");
            this.header = false;
        } else {
            this.writer.write(",\n");
        }
        this.writer.write(((JsonWritable)writable).toJson());
    }

    @Override
    public void close() throws IOException {
        if (!this.header){
            this.writer.write("]");
        }
        this.writer.close();
    }
}
