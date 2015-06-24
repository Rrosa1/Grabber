package cl.tesis.input;

import java.io.IOException;

public interface FileWriter {

    void writeLine(ListWritable writable) throws IOException;
}
