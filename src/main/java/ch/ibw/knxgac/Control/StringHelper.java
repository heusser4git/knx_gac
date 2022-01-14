package ch.ibw.knxgac.Control;

import java.util.ArrayList;

public class StringHelper {
    public static String implode(ArrayList<String> list, String separator) {
        // Todo Dokumentation Quelle: https://www.demo2s.com/java/java-arraylist-implode-arraylist-string-list-string-separator.html
        StringBuilder sb = new StringBuilder();
        int size = list != null ? list.size() : 0;
        for (int i = 0; i < size; i++) {
            sb.append(list.get(i));
            if (i != (size - 1)) {
                sb.append(separator);
            }
        }
        return sb.toString();
    }
}
