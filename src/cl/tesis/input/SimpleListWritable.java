package cl.tesis.input;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class SimpleListWritable implements ListWritable{

    @Override
    public List<String> getParameterList() {
        ArrayList<String> parameters =  new ArrayList<>();
        Class aClass =  this.getClass();
        Field[] fields = aClass.getDeclaredFields();

        for (Field field : fields) {
            parameters.add(field.getName());
        }

        return parameters;
    }

    @Override
    public List<String> getValueList() {
        ArrayList<String> values = new ArrayList<>();

        try {
            Class aClass = this.getClass();
            Field[] fields = aClass.getDeclaredFields();

            for (Field field : fields) {
                field.setAccessible(true);
                values.add(field.get(this) + "");
                field.setAccessible(false);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return values;
    }
}
