package ch.ibw.knxgac.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MiddleGroupTest {

    @Test
    void getWhereClause_WithNameWithoutIdMiddlegroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setName("MiddleGroup 1");
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'MiddleGroup 1%' AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithidMaingroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setIdMaingroup(1);
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND idMaingroup = 1 AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdMiddlegroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setId(1);
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID = 1 AND deleted <> 1", s);
    }

    @Test
    void getUpdateClause_WithIdMiddlegroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setId(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '' , number = 0 , idMaingroup = 0", s);
    }
    @Test
    void getUpdateClause_onlyWithName() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setName("MiddleGroup 1");
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = 'MiddleGroup 1' , number = 0 , idMaingroup = 0", s);
    }
    @Test
    void getUpdateClause_WithidMaingroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setIdMaingroup(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '' , number = 0 , idMaingroup = 1", s);
    }
    @Test
    void getUpdateClause_WithNumber() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setNumber(123456);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '' , number = 123456 , idMaingroup = 0", s);
    }

    @Test
    void getUpdateClause_WithNameAndNumberAndidMaingroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setName("MiddleGroup 1");
        m.setNumber(123456);
        m.setIdMaingroup(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = 'MiddleGroup 1' , number = 123456 , idMaingroup = 1", s);
    }
}