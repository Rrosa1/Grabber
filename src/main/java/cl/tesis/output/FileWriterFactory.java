package cl.tesis.output;

public class FileWriterFactory {

    public FileWriter getFileWriter(String fileWriterType, String fileName) {
        if (fileWriterType == null)
            return null;

        switch (fileWriterType) {
            case "JSON":
                return new JsonFileWriter(fileName);
            default:
                return null;
        }
    }
}
