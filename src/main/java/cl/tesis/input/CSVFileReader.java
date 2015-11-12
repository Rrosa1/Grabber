package cl.tesis.input;

import org.omg.SendingContext.RunTime;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFileReader implements  FileReader{
    private static final int TICKS = 100;
    private static final int MB = 1024 * 1024;
    private static final String CSV_SEPARATOR = ",";
    private static final Logger logger = Logger.getLogger(CSVFileReader.class.getName());

    private int readLines;
    private String fileName;
    private BufferedReader reader;
    private Runtime runtime;

    public CSVFileReader(String fileName) {
        this.readLines = 0;
        this.fileName = fileName;
        this.runtime = Runtime.getRuntime();
        try {
            this.reader = new BufferedReader(new java.io.FileReader(this.fileName));
        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "File not found {0}", this.fileName);
            System.exit(0);
        }

    }

    @Override
    public synchronized String[] nextLine(){
        String line;

        try{
            line = this.reader.readLine();
            this.readLines++;

            if (this.readLines % TICKS == 0 && line!= null) {
                logger.log(Level.INFO, "Lines processed {0}", this.readLines);
                logger.log(Level.INFO, "Current Total memory {0}, Free memory {1}, Max memory {2}", new String[]{Long.toString(this.runtime.totalMemory()/MB), Long.toString(this.runtime.freeMemory()/MB), Long.toString(runtime.maxMemory()/MB)});
            }
        } catch (IOException error) {
            return null;
        }

        if (line != null){
            return line.split(CSV_SEPARATOR);
        }

        return null;
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

}
