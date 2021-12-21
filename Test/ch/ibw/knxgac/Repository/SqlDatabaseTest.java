package ch.ibw.knxgac.Repository;

import ch.ibw.knxgac.Model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SqlDatabaseTest {

    @Test
    void getSqlInsertQuery_withTestName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlInsertQuery("Project", "name='test'");
        // Assert
        assertEquals(result, "INSERT INTO Project SET name='test';");
    }

    @Test
    void getSqlUpdateQuery_ProjectWithName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Project project = new Project(1);
        project.setName("Testname");
        // Act
        String result = sql.getSqlUpdateQuery("Project", project);
        // Assert
        assertEquals("UPDATE Project SET name = 'Testname', number = 0 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_ProjectWithNumber() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Project project = new Project(1);
        project.setNumber(12345678);
        // Act
        String result = sql.getSqlUpdateQuery("Project", project);
        // Assert
        assertEquals("UPDATE Project SET name = '', number = 12345678 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_ProjectWithNameAndNumber() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Project project = new Project(1);
        project.setName("Testname");
        project.setNumber(12345);
        // Act
        String result = sql.getSqlUpdateQuery("Project", project);
        // Assert
        assertEquals("UPDATE Project SET name = 'Testname', number = 12345 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MaingroupWithName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MainGroup mainGroup = new MainGroup(1);
        mainGroup.setName("Testname");
        // Act
        String result = sql.getSqlUpdateQuery("Maingroup", mainGroup);
        // Assert
        assertEquals("UPDATE Maingroup SET name = 'Testname', number = 0 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MaingroupWithNumber() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MainGroup mainGroup = new MainGroup(1);
        mainGroup.setNumber(123456);
        // Act
        String result = sql.getSqlUpdateQuery("Maingroup", mainGroup);
        // Assert
        assertEquals("UPDATE Maingroup SET name = '', number = 123456 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MaingroupWithNameAndNumber() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MainGroup mainGroup = new MainGroup(1);
        mainGroup.setName("Testname");
        mainGroup.setNumber(123456);
        // Act
        String result = sql.getSqlUpdateQuery("Maingroup", mainGroup);
        // Assert
        assertEquals("UPDATE Maingroup SET name = 'Testname', number = 123456 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MaingroupWithIdProject() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MainGroup mainGroup = new MainGroup(1);
        mainGroup.setIdProject(1);
        // Act
        String result = sql.getSqlUpdateQuery("Maingroup", mainGroup);
        // Assert
        assertEquals("UPDATE Maingroup SET name = '', number = 0, idProject = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MaingroupWithNameAndNumberAndIdProject() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MainGroup mainGroup = new MainGroup(1);
        mainGroup.setName("Testname");
        mainGroup.setNumber(123456);
        mainGroup.setIdProject(1);
        // Act
        String result = sql.getSqlUpdateQuery("Maingroup", mainGroup);
        // Assert
        assertEquals("UPDATE Maingroup SET name = 'Testname', number = 123456, idProject = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MaingroupWithNameEqualsNullAndNumberAndIdProject() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MainGroup mainGroup = new MainGroup(1);
        mainGroup.setName(null);
        mainGroup.setNumber(123456);
        mainGroup.setIdProject(1);
        // Act
        String result = sql.getSqlUpdateQuery("Maingroup", mainGroup);
        // Assert
        assertEquals("UPDATE Maingroup SET number = 123456, idProject = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MiddlegroupWithName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MiddleGroup middleGroup = new MiddleGroup(1);
        middleGroup.setName("Testname");
        // Act
        String result = sql.getSqlUpdateQuery("Middlegroup", middleGroup);
        // Assert
        assertEquals("UPDATE Middlegroup SET name = 'Testname', number = 0 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MiddlegroupWithNumber() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MiddleGroup middleGroup = new MiddleGroup(1);
        middleGroup.setNumber(12345678);
        // Act
        String result = sql.getSqlUpdateQuery("Middlegroup", middleGroup);
        // Assert
        assertEquals("UPDATE Middlegroup SET name = '', number = 12345678 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MiddlegroupWithNameAndNumber() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MiddleGroup middleGroup = new MiddleGroup(1);
        middleGroup.setName("Testname");
        middleGroup.setNumber(12345678);
        // Act
        String result = sql.getSqlUpdateQuery("Middlegroup", middleGroup);
        // Assert
        assertEquals("UPDATE Middlegroup SET name = 'Testname', number = 12345678 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MiddlegroupWithIdMaingroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MiddleGroup middleGroup = new MiddleGroup(1);
        middleGroup.setIdMaingroup(1);
        // Act
        String result = sql.getSqlUpdateQuery("Middlegroup", middleGroup);
        // Assert
        assertEquals("UPDATE Middlegroup SET name = '', number = 0, idMaingroup = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MiddlegroupWithNameAndNumberAndIdMaingroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MiddleGroup middleGroup = new MiddleGroup(1);
        middleGroup.setName("Testname");
        middleGroup.setNumber(12345678);
        middleGroup.setIdMaingroup(1);
        // Act
        String result = sql.getSqlUpdateQuery("Middlegroup", middleGroup);
        // Assert
        assertEquals("UPDATE Middlegroup SET name = 'Testname', number = 12345678, idMaingroup = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_MiddlegroupWithNameEqualsNullAndNumberAndIdMaingroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        MiddleGroup middleGroup = new MiddleGroup(1);
        middleGroup.setName(null);
        middleGroup.setNumber(12345678);
        middleGroup.setIdMaingroup(1);
        // Act
        String result = sql.getSqlUpdateQuery("Middlegroup", middleGroup);
        // Assert
        assertEquals("UPDATE Middlegroup SET number = 12345678, idMaingroup = 1 WHERE id=1;", result);
    }


    @Test
    void getSqlUpdateQuery_AddressWithName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Address address = new Address(1);
        address.setName("Testname");
        // Act
        String result = sql.getSqlUpdateQuery("Address", address);
        // Assert
        assertEquals("UPDATE Address SET name = 'Testname', startaddress = 0 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AddressWithStartaddress() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Address address = new Address(1);
        address.setStartAddress(12345678);
        // Act
        String result = sql.getSqlUpdateQuery("Address", address);
        // Assert
        assertEquals("UPDATE Address SET name = '', startaddress = 12345678 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AddressWithNameAndStartaddress() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Address address = new Address(1);
        address.setName("Testname");
        address.setStartAddress(12345678);
        // Act
        String result = sql.getSqlUpdateQuery("Address", address);
        // Assert
        assertEquals("UPDATE Address SET name = 'Testname', startaddress = 12345678 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AddressWithIdMiddlegroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Address address = new Address(1);
        address.setIdMiddlegroup(1);
        // Act
        String result = sql.getSqlUpdateQuery("Address", address);
        // Assert
        assertEquals("UPDATE Address SET name = '', startaddress = 0, idMiddlegroup = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AddressWithNameAndStartaddressAndIdMiddlegroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Address address = new Address(1);
        address.setName("Testname");
        address.setStartAddress(12345678);
        address.setIdMiddlegroup(1);
        // Act
        String result = sql.getSqlUpdateQuery("Address", address);
        // Assert
        assertEquals("UPDATE Address SET name = 'Testname', startaddress = 12345678, idMiddlegroup = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AddressWithNameEqualsNullAndStartaddressAndIdMiddlegroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Address address = new Address(1);
        address.setName(null);
        address.setStartAddress(12345678);
        address.setIdMiddlegroup(1);
        // Act
        String result = sql.getSqlUpdateQuery("Address", address);
        // Assert
        assertEquals("UPDATE Address SET startaddress = 12345678, idMiddlegroup = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_ObjectTemplateWithName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        ObjectTemplate objectTemplate = new ObjectTemplate(1);
        objectTemplate.setName("Testname");
        // Act
        String result = sql.getSqlUpdateQuery("ObjectTemplate", objectTemplate);
        // Assert
        assertEquals("UPDATE ObjectTemplate SET name = 'Testname' WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_ObjectTemplateWithIdAddress() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        ObjectTemplate objectTemplate = new ObjectTemplate(1);
        objectTemplate.setIdAddress(1);
        // Act
        String result = sql.getSqlUpdateQuery("ObjectTemplate", objectTemplate);
        // Assert
        assertEquals("UPDATE ObjectTemplate SET name = '', idAddress = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_ObjectTemplateWithNameAndIdAddress() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        ObjectTemplate objectTemplate = new ObjectTemplate(1);
        objectTemplate.setName("Testname");
        objectTemplate.setIdAddress(1);
        // Act
        String result = sql.getSqlUpdateQuery("ObjectTemplate", objectTemplate);
        // Assert
        assertEquals("UPDATE ObjectTemplate SET name = 'Testname', idAddress = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_ObjectTemplateWithNameEqualsNullAndIdAddress() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        ObjectTemplate objectTemplate = new ObjectTemplate(1);
        objectTemplate.setName(null);
        objectTemplate.setIdAddress(1);
        // Act
        String result = sql.getSqlUpdateQuery("ObjectTemplate", objectTemplate);
        // Assert
        assertEquals("UPDATE ObjectTemplate SET idAddress = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AttributeWithName() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Attribute attribute = new Attribute(1);
        attribute.setName("Testname");
        // Act
        String result = sql.getSqlUpdateQuery("Attribute", attribute);
        // Assert
        assertEquals("UPDATE Attribute SET name = 'Testname' WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AttributeWithIdObjectTemplate() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Attribute attribute = new Attribute(1);
        attribute.setIdObjectTemplate(1);
        // Act
        String result = sql.getSqlUpdateQuery("Attribute", attribute);
        // Assert
        assertEquals("UPDATE Attribute SET name = '', idObjectTemplate = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AttributeWithNameAndIdObjectTemplate() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Attribute attribute = new Attribute(1);
        attribute.setName("Testname");
        attribute.setIdObjectTemplate(1);
        // Act
        String result = sql.getSqlUpdateQuery("Attribute", attribute);
        // Assert
        assertEquals("UPDATE Attribute SET name = 'Testname', idObjectTemplate = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlUpdateQuery_AttributeWithNameEqualsNullAndIdObjectTemplate() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        Attribute attribute = new Attribute(1);
        attribute.setName(null);
        attribute.setIdObjectTemplate(1);
        // Act
        String result = sql.getSqlUpdateQuery("Attribute", attribute);
        // Assert
        assertEquals("UPDATE Attribute SET idObjectTemplate = 1 WHERE id=1;", result);
    }

    @Test
    void getSqlDeleteQuery_Project() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlDeleteQuery("Project", 1);
        // Assert
        assertEquals("UPDATE Project SET deleted=1 WHERE id=1;", result);
    }

    @Test
    void getSqlDeleteQuery_Maingroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlDeleteQuery("Maingroup", 1);
        // Assert
        assertEquals("UPDATE Maingroup SET deleted=1 WHERE id=1;", result);
    }

    @Test
    void getSqlDeleteQuery_Middlegroup() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlDeleteQuery("Middlegroup", 1);
        // Assert
        assertEquals("UPDATE Middlegroup SET deleted=1 WHERE id=1;", result);
    }

    @Test
    void getSqlDeleteQuery_Address() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlDeleteQuery("Address", 1);
        // Assert
        assertEquals("UPDATE Address SET deleted=1 WHERE id=1;", result);
    }

    @Test
    void getSqlDeleteQuery_ObjectTemplate() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlDeleteQuery("ObjectTemplate", 1);
        // Assert
        assertEquals("UPDATE ObjectTemplate SET deleted=1 WHERE id=1;", result);
    }

    @Test
    void getSqlDeleteQuery_Attribute() {
        // Arrange
        SqlDatabase sql = new SqlDatabase();
        // Act
        String result = sql.getSqlDeleteQuery("Attribute", 1);
        // Assert
        assertEquals("UPDATE Attribute SET deleted=1 WHERE id=1;", result);
    }

}