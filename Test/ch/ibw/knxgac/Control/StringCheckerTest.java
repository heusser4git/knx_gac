package ch.ibw.knxgac.Control;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCheckerTest {

    @Test
    void checkStringFirstDigitIsLetter_FirstIsNumber_ReturnsFalse() {
        // Arrange
        String toCheck = "3test";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringFirstDigitIsLetter_FirstIsUmlaut_ReturnsFalse() {
        // Arrange
        String toCheck = "ätest";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringFirstDigitIsLetter_FirstIsSlash_ReturnsFalse() {
        // Arrange
        String toCheck = "/test";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringFirstDigitIsLetter_FirstIsLetter_ReturnsTrue() {
        // Arrange
        String toCheck = "test";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withExclamationMark_ReturnsFalse() {
        // Arrange
        String toCheck = "te!Efsd fd";
        // Act
        boolean result = StringChecker.checkStringLettersSpacesNumbersUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withSlash_ReturnsFalse() {
        // Arrange
        String toCheck = "teEf/sd fd";
        // Act
        boolean result = StringChecker.checkStringLettersSpacesNumbersUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withSpecialChar_ReturnsFalse() {
        // Arrange
        String toCheck = "teEf$sd fd";
        // Act
        boolean result = StringChecker.checkStringLettersSpacesNumbersUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withSpace_ReturnsTrue() {
        // Arrange
        String toCheck = "teEfsd fd";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withUmlaut_ReturnsTrue() {
        // Arrange
        String toCheck = "teEfsdöfd";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withNumber_ReturnsTrue() {
        // Arrange
        String toCheck = "teEfsd2fd";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withNumberFirst_ReturnsTrue() {
        // Arrange
        String toCheck = "2teEfsdfd";
        // Act
        boolean result = StringChecker.checkStringLettersSpacesNumbersUmlaute(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withMultipleCases_ReturnsTrue() {
        // Arrange
        String toCheck = "2te äEf Äsdfd";
        // Act
        boolean result = StringChecker.checkStringLettersSpacesNumbersUmlaute(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringLettersSpacesNumbersUmlaute_withOnlyLetters_ReturnsTrue() {
        // Arrange
        String toCheck = "AbcdEf";
        // Act
        boolean result = StringChecker.checkStringFirstDigitIsLetter(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringOnlyLettersNoUmlaute_withSlash_ReturnsFalse() {
        // Arrange
        String toCheck = "Ab/cdEf";
        // Act
        boolean result = StringChecker.checkStringOnlyLettersNoUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyLettersNoUmlaute_withUmlaut_ReturnsFalse() {
        // Arrange
        String toCheck = "AbÄcdEf";
        // Act
        boolean result = StringChecker.checkStringOnlyLettersNoUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyLettersNoUmlaute_withSpace_ReturnsFalse() {
        // Arrange
        String toCheck = "Ab cdEf";
        // Act
        boolean result = StringChecker.checkStringOnlyLettersNoUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyLettersNoUmlaute_withSpecialChar_ReturnsFalse() {
        // Arrange
        String toCheck = "Ab$cdEf";
        // Act
        boolean result = StringChecker.checkStringOnlyLettersNoUmlaute(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyLettersNoUmlaute_withOnlyLetters_ReturnsTrue() {
        // Arrange
        String toCheck = "AllesFunktioniert";
        // Act
        boolean result = StringChecker.checkStringOnlyLettersNoUmlaute(toCheck);
        // Assert
        assertTrue(result);
    }

    @Test
    void checkStringOnlyNumbers_withSpace_ReturnsFalse() {
        // Arrange
        String toCheck = "12 34";
        // Act
        boolean result = StringChecker.checkStringOnlyNumbers(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyNumbers_withLetter_ReturnsFalse() {
        // Arrange
        String toCheck = "12y34";
        // Act
        boolean result = StringChecker.checkStringOnlyNumbers(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyNumbers_withSpecialChar_ReturnsFalse() {
        // Arrange
        String toCheck = "12$34";
        // Act
        boolean result = StringChecker.checkStringOnlyNumbers(toCheck);
        // Assert
        assertFalse(result);
    }

    @Test
    void checkStringOnlyNumbers_withOnlyNumbers_ReturnsTrue() {
        // Arrange
        String toCheck = "1234";
        // Act
        boolean result = StringChecker.checkStringOnlyNumbers(toCheck);
        // Assert
        assertTrue(result);
    }
}