package ch.ibw.knxgac.Control;

public class StringChecker {
    public static boolean checkStringFirstDigitIsLetter(String string) {
        string = String.valueOf(string.charAt(0));
        // A-Z oder a-z
        if(string.matches("[a-zA-Z]")) {
            return true;
        }
        return false;
    }
    public static boolean checkStringLettersSpacesNumbersUmlaute(String string) {
        if(string.matches("[a-zA-Z0-9 äöüÄÖÜ]*"))
            return true;
        return false;
    }
    public static boolean checkStringOnlyLettersNoUmlaute(String string) {
        if(string.matches("[a-zA-Z]*"))
            return true;
        return false;
    }
    public static boolean checkStringOnlyNumbers(String string) {
        if(string.matches("[0-9]*"))
            return true;
        return false;
    }
}
