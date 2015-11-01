package cl.tesis.output;

import java.util.List;

public interface CSVWritable extends Writable{

    List<String> getParameterList();

    List<String> getValueList();

}
