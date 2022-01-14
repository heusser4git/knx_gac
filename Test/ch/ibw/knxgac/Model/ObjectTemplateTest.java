package ch.ibw.knxgac.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class ObjectTemplateTest {

    @Test
    void getWhereClause_WithNameWithoutIdObjectTemplate() {
        // Arrange
        ObjectTemplate o = new ObjectTemplate();
        o.setName("ObjectTemplate 1");
        // Act
        String s = o.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'ObjectTemplate 1%' AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdObjectTemplate() {
        // Arrange
        ObjectTemplate o = new ObjectTemplate();
        o.setId(1);
        // Act
        String s = o.getWhereClause();
        // Assert
        assertEquals("ID = 1 AND deleted <> 1", s);
    }

    @Test
    void getUpdateClause_WithIdObjectTemplate() {
        // Arrange
        ObjectTemplate o = new ObjectTemplate();
        o.setId(1);
        // Act
        String s = o.getUpdateClause();
        // Assert
        assertEquals("name = ''", s);
    }

    @Test
    void getUpdateClause_onlyWithName() {
        // Arrange
        ObjectTemplate o = new ObjectTemplate();
        o.setName("ObjectTemplate 1");
        // Act
        String s = o.getUpdateClause();
        // Assert
        assertEquals("name = 'ObjectTemplate 1'", s);
    }

    @Test
    void availableStartadresses() {
        // Arrange
        ObjectTemplate objectTemplate = new ObjectTemplate(1);
        objectTemplate.addAttribute(new Attribute(1));
        objectTemplate.addAttribute(new Attribute(2));
        ArrayList<Integer> maxStartadresses = new ArrayList<>();
        for (int i = 0; i <7; i++) {
            maxStartadresses.add(i);
        }
        ArrayList<Integer> usedStartadresses = new ArrayList<>();
        usedStartadresses.add(2);

        // Act
        ArrayList<Integer> result = objectTemplate.availableStartadresses(maxStartadresses, usedStartadresses);

        // Assert
        Assertions.assertEquals(4, result.size());
        Assertions.assertEquals(0, result.get(0));
        Assertions.assertEquals(3, result.get(1));
        Assertions.assertEquals(4, result.get(2));
        Assertions.assertEquals(5, result.get(3));
    }
}