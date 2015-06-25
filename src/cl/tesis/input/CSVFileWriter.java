package cl.tesis.input;

import java.io.*;
import java.util.List;

public class CSVFileWriter implements FileWriter {
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
            System.out.println("File not found");
            System.exit(0);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            System.exit(0);
        }
    }

    @Override
    public void writeLine(ListWritable writable) throws IOException {
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
}
