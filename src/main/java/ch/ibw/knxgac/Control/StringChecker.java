package ch.ibw.knxgac.Control;

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
}
