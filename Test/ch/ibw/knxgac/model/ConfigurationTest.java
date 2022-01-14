package ch.ibw.knxgac.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConfigurationTest {
    @Test
    void configComplete_configEmpty_returnFalse() {
        // Arrange
        Configuration configuration = new Configuration();
        // Act
        boolean result = configuration.configComplete();
        // Assert
        assertFalse(result);
    }
    @Test
    void configComplete_configFilled_returnTrue() {
        // Arrange
        Configuration configuration = new Configuration();
        configuration.setDbServertyp(Servertyp.MARIADB);
        configuration.setDbServer("localhost");
        configuration.setDbServerPort(1234);
        configuration.setDbName("dbname");
        configuration.setDbUsername("user");
        configuration.setDbPassword("password");
        configuration.setCsvOutputpath("/");
        // Act
        boolean result = configuration.configComplete();
        // Assert
        assertTrue(result);
    }
    @Test
    void configComplete_configFilledButWithNull_returnFalse() {
        // Arrange
        Configuration configuration = new Configuration();
        configuration.setDbServertyp(Servertyp.MARIADB);
        configuration.setDbServer("localhost");
        configuration.setDbServerPort(1234);
        configuration.setDbName("dbname");
        configuration.setDbUsername(null);
        configuration.setDbPassword("password");
        configuration.setCsvOutputpath("/");
        // Act
        boolean result = configuration.configComplete();
        // Assert
        assertFalse(result);
    }
}