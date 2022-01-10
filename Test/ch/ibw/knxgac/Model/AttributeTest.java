package ch.ibw.knxgac.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AttributeTest {
    @Test
    void getWhereClause_WithNameWithoutIdAttribute() {
        // Arrange
        Attribute a = new Attribute();
        a.setName("Attribute 1");
        // Act
        String s = a.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'Attribute 1%' AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdObjectTemplate() {
        // Arrange
        Attribute a = new Attribute();
        a.setIdObjectTemplate(1);
        // Act
        String s = a.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND idObjectTemplate = 1 AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdAttribute() {
        // Arrange
        Attribute m = new Attribute();
        m.setId(1);
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID = 1 AND deleted <> 1", s);
    }

    @Test
    void getUpdateClause_WithIdAttribute() {
        // Arrange
        Attribute m = new Attribute();
        m.setId(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 0", s);
    }
    @Test
    void getUpdateClause_onlyWithName() {
        // Arrange
        Attribute m = new Attribute();
        m.setName("Attribute 1");
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = 'Attribute 1', number = 0", s);
    }
    @Test
    void getUpdateClause_WithIdObjectTemplate() {
        // Arrange
        Attribute a = new Attribute();
        a.setIdObjectTemplate(1);
        // Act
        String s = a.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 0, idObjectTemplate = 1", s);
    }

}