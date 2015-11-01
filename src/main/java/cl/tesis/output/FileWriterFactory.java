package cl.tesis.output;

import java.io.File;

public class FileWriterFactory {

    public FileWriter getFileWriter(String fileWriterType, String fileName) {
        if (fileWriterType == null)
            return null;

        switch (fileWriterType) {
            case "CSV":
                return new CSVFileWriter(fileName);
            case "JSON":
                return new JsonFileWriter(fileName);
            default:
                return null;
        }
    }
}
