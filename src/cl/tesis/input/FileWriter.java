package cl.tesis.input;

import java.io.Closeable;
import java.io.IOException;

public interface FileWriter extends Closeable{

    void writeLine(ListWritable writable) throws IOException;
}
