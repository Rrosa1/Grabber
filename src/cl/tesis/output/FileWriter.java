package cl.tesis.output;

import java.io.Closeable;
import java.io.IOException;

public interface FileWriter extends Closeable{

    void writeLine(CSVWritable writable) throws IOException;
}
