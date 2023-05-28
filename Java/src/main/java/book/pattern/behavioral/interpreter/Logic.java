package book.pattern.behavioral.interpreter;

import java.util.HashMap;
import java.util.Map;

public interface Logic {
    boolean evaluate();

    public static class Values {
        static Map<String, Boolean> vars = new HashMap<>();
        static void assign(String key, boolean value) {
            if(key == null || key.length() <= 0){
                throw new RuntimeException("Assign falied");
            }
            vars.put(key, value ? Boolean.TRUE : Boolean.FALSE);
        }

        static boolean lookup(String key) {
            Object got = vars.get(key);
            return (Boolean) got;
        }
    }
}
