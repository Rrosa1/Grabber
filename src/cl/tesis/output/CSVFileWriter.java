package cl.tesis.output;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CSVFileWriter implements cl.tesis.output.FileWriter {
    private static final Logger logger = Logger.getLogger(CSVFileWriter.class.getName());

    private static final String CSV_SEPARATOR = ",";
    private static final String NEW_LINE = "\n";

    private String fileName;
    private BufferedWriter writer;
    private boolean header;

    public CSVFileWriter(String fileName) {
        this.fileName = fileName;
        this.header = false;
        try {
            this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(this.fileName), "utf-8"));
        } catch (FileNotFoundException e) {
            logger.log(Level.INFO, "File not found {0}", this.fileName);
            System.exit(0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public synchronized void writeLine(ListWritable writable) throws IOException {
        if (!this.header) {
            this.writer.write(toCSV(writable.getParameterList()));
            this.header = true;
        }

        this.writer.write(toCSV(writable.getValueList()));
    }

    private String toCSV(List<String> list) {
        String csv = "";
        for (String s : list) {
            csv += s;
            csv += CSV_SEPARATOR;
        }

        return csv.substring(0, csv.length() - 1) + NEW_LINE;
    }

    @Override
    public void close() throws IOException {
        this.writer.close();
    }
}
