package src;
import java.util.ArrayList;

public class Utilities {
    public static String listWithComma(ArrayList<String> list) {
        String result = "";
        boolean first = true;
        for (String item: list) {
            if (first) {
                first = false;
            } else {
                result += ", ";
            }
            result += item;
        }
        return result;
    }
}
