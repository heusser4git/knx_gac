package ch.ibw.knxgac.Model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AddressTest {
    @Test
    void getWhereClause_WithNameWithoutIdAddress() {
        // Arrange
        Address a = new Address();
        a.setName("Address 1");
        // Act
        String s = a.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND name LIKE 'Address 1%' AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdMiddlegroup() {
        // Arrange
        Address a = new Address();
        a.setIdMiddlegroup(1);
        // Act
        String s = a.getWhereClause();
        // Assert
        assertEquals("ID is not NULL AND idMiddlegroup = 1 AND deleted <> 1", s);
    }

    @Test
    void getWhereClause_WithIdAddress() {
        // Arrange
        Address a = new Address();
        a.setId(1);
        // Act
        String s = a.getWhereClause();
        // Assert
        assertEquals("ID = 1 AND deleted <> 1", s);
    }

    @Test
    void getUpdateClause_WithIdAddress() {
        // Arrange
        Address a = new Address();
        a.setId(1);
        // Act
        String s = a.getUpdateClause();
        // Assert
        assertEquals("name = '', startaddress = 0", s);
    }
    @Test
    void getUpdateClause_onlyWithName() {
        // Arrange
        Address a = new Address();
        a.setName("Address 1");
        // Act
        String s = a.getUpdateClause();
        // Assert
        assertEquals("name = 'Address 1', startaddress = 0", s);
    }
    @Test
    void getUpdateClause_WithIdMiddlegroup() {
        // Arrange
        Address a = new Address();
        a.setIdMiddlegroup(1);
        // Act
        String s = a.getUpdateClause();
        // Assert
        assertEquals("name = '', startaddress = 0, idMiddlegroup = 1", s);
    }
    @Test
    void getUpdateClause_WithStartaddress() {
        // Arrange
        Address a = new Address();
        a.setStartAddress(123456);
        // Act
        String s = a.getUpdateClause();
        // Assert
        assertEquals("name = '', startaddress = 123456", s);
    }

    @Test
    void getUpdateClause_WithNameAndStartaddressAndIdMiddlegroup() {
        // Arrange
        Address a = new Address();
        a.setName("Address 1");
        a.setStartAddress(123456);
        a.setIdMiddlegroup(1);
        // Act
        String s = a.getUpdateClause();
        // Assert
        assertEquals("name = 'Address 1', startaddress = 123456, idMiddlegroup = 1", s);
    }
}