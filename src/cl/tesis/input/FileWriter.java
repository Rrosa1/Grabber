package cl.tesis.input;

import java.io.IOException;
import java.util.List;

public interface FileWriter {

    void writeLine(ListWritable writable) throws IOException;
}
