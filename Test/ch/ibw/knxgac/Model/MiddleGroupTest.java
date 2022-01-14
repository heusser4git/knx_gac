package ch.ibw.knxgac.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.ArrayList;

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
        assertEquals("name = '', number = 0", s);
    }
    @Test
    void getUpdateClause_onlyWithName() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setName("MiddleGroup 1");
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = 'MiddleGroup 1', number = 0", s);
    }
    @Test
    void getUpdateClause_WithidMaingroup() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setIdMaingroup(1);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 0, idMaingroup = 1", s);
    }
    @Test
    void getUpdateClause_WithNumber() {
        // Arrange
        MiddleGroup m = new MiddleGroup();
        m.setNumber(123456);
        // Act
        String s = m.getUpdateClause();
        // Assert
        assertEquals("name = '', number = 123456", s);
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
        assertEquals("name = 'MiddleGroup 1', number = 123456, idMaingroup = 1", s);
    }

    @Test
    void usedAddresses() {
        // Arrange
        MiddleGroup middleGroup = new MiddleGroup(1);
        Address address = new Address(1);
        address.setStartAddress(4);
        ObjectTemplate objectTemplate = new ObjectTemplate(1);
        Attribute attribute = new Attribute(1);
        attribute.setNumber(5);
        objectTemplate.addAttribute(attribute);
        Attribute attribute2 = new Attribute(2);
        attribute2.setNumber(1);
        objectTemplate.addAttribute(attribute2);
        address.setObjectTemplate(objectTemplate);
        middleGroup.addAddress(address);

        // Act
        ArrayList<Integer> usedAdresses = middleGroup.usedAddresses();

        // Assert
        Assertions.assertEquals(2, usedAdresses.size());
        Assertions.assertEquals(9, usedAdresses.get(0));
        Assertions.assertEquals(5, usedAdresses.get(1));
    }
}