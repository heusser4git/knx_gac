package ch.ibw.knxgac.Control;

import java.io.File;
import java.io.IOException;

public class StringChecker {
    public static boolean checkStringFirstDigitIsLetter(String string) {
        string = String.valueOf(string.charAt(0));
        // A-Z oder a-z
        return string.matches("[a-zA-Z]");
    }
    public static boolean checkStringLettersSpacesNumbersUmlaute(String string) {
        return string.matches("[a-zA-Z0-9 äöüÄÖÜ]*");
    }
    public static boolean checkStringOnlyLettersNoUmlaute(String string) {
        return string.matches("[a-zA-Z]*");
    }
    public static boolean checkStringOnlyNumbers(String string) {
        return string.matches("[0-9]*");
    }

    /**
     * Checks if a directory is writable
     * @param path  String  Directory-Path
     * @return boolean  true if its wirtable, false if not
     * @throws IOException
     */
    public static boolean directoryWriteFilePermission(String path) throws IOException {
        File p = new File(path);
        if(!p.canWrite()) {
            return false;
        }
        // canwrite is not allways working -> test a real writing
        File file = new File(path + "/testfile.txt");
        if(!file.createNewFile()) {
            return false;
        } else {
            // delete the testfile again
            file.delete();
            return true;
        }
    }
}
