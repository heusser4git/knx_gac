package ch.ibw.knxgac.model;

import org.junit.jupiter.api.Test;

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
}