package ch.ibw.knxgac.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MainGroupTest {

    @Test
    void getWhereClause_WithNameWithoutIdMaingroup() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setName("Maingroup 1");
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'Maingroup 1%' AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdProject() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setIdProject(1);
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND idProject = 1 AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdMaingroup() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setId(1);
        // Act
        String s = m.getWhereClause();
        // Assert
        assertEquals("ID = 1 AND deleted <> 1", s);
    }

    @Test
    void getUpdateClause_WithIdMaingroup() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setId(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 0", s);
    }
    @Test
    void getUpdateClause_onlyWithName() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setName("Maingroup 1");
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = 'Maingroup 1', number = 0", s);
    }
    @Test
    void getUpdateClause_WithIdProject() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setIdProject(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 0, idProject = 1", s);
    }
    @Test
    void getUpdateClause_WithNumber() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setNumber(123456);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 123456", s);
    }

    @Test
    void getUpdateClause_WithNameAndNumberAndIdProject() {
        // Arrange
        MainGroup m = new MainGroup();
        m.setName("Maingroup 1");
        m.setNumber(123456);
        m.setIdProject(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = 'Maingroup 1', number = 123456, idProject = 1", s);
    }
}