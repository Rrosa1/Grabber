package cl.tesis.input;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class CSVFileReader implements  FileReader{
    private static final String CSV_SEPARATOR = ",";
    private String fileName;
    private BufferedReader reader;

    public CSVFileReader(String fileName) {
        this.fileName = fileName;
        try {
            this.reader = new BufferedReader(new java.io.FileReader(this.fileName));
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(0);
        }

    }

    @Override
    public synchronized String[] nextLine(){
        String line;

        try{
            line = this.reader.readLine();
        } catch (IOException error) {
            return null;
        }

        if (line != null){
            return line.split(CSV_SEPARATOR);
        }

        return null;
    }

    @Override
    public void close() {
        try {
            this.reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
