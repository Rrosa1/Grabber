package cl.tesis.input;

import java.io.Closeable;

public interface FileReader extends Closeable {

    String[] nextLine();

}
