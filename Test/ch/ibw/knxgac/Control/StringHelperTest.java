package ch.ibw.knxgac.Control;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

class StringHelperTest {

    @Test
    void implode() {
        // Arrange
        ArrayList<String> arrayList = new ArrayList<>();
        arrayList.add("Hans");
        arrayList.add("Wurst");
        // Act
        String result = StringHelper.implode(arrayList, ", ");
        // Assert
        Assertions.assertEquals("Hans, Wurst", result);
    }
}