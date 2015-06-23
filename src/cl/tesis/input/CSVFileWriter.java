package cl.tesis.input;

import java.io.BufferedWriter;
import java.io.IOException;
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
