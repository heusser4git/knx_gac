package ch.ibw.knxgac.Model;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {

    @Test
    void getWhereClause_withIdProject() {
        // Arrange
        Project p = new Project();
        p.setId(1);
        p.setName("Projekt 1");
        // Act
        String s = p.getWhereClause();
        // Assert
        assertEquals("ID = 1 AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_withNameWithoutIdProject() {
        // Arrange
        Project p = new Project();
        p.setName("Projekt 1");
        // Act
        String s = p.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'Projekt 1%' AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_withNumberWithoutIdProject() {
        // Arrange
        Project p = new Project();
        p.setNumber(123456);
        // Act
        String s = p.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND number = 123456 AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_withNameAndNumberWithoutIdProject() {
        // Arrange
        Project p = new Project();
        p.setName("Projekt 1");
        p.setNumber(123456);
        // Act
        String s = p.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'Projekt 1%' AND number = 123456 AND deleted <> 1", s);
    }
}