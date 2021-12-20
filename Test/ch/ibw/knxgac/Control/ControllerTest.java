package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.Configuration;
import ch.ibw.knxgac.Repository.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class ControllerTest {
    @Test
    void saveConfiguration() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void getConfiguration() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void checkConfiguration() throws SQLException, IOException, ClassNotFoundException {
        // Arrange
        Database db = mock(Database.class);
        Configuration configuration = mock(Configuration.class);
        Controller controller = new Controller(db);

        doReturn(true).when(configuration).configComplete();
        when(db.createConnection(anyString(), anyString(), anyInt(), anyString(), anyString(), anyString())).thenReturn(true);
        doReturn(true).when(db).isConnected();
//        when(db.createConnection(configuration.getDbServertyp().name(), configuration.getDbServer(), configuration.getDbServerPort(), configuration.getDbName(), configuration.getDbUsername(), configuration.getDbPassword())).thenReturn(true);
//        doReturn(true).when(db.createConnection(configuration.getDbServertyp().name(), configuration.getDbServer(), configuration.getDbServerPort(), configuration.getDbName(), configuration.getDbUsername(), configuration.getDbPassword()));
//        doReturn(true).when(db).createConnection(Servertyp.MARIADB.name(), "server", 1234, "database", "user", "pwd");
        // Act
        boolean result = controller.checkConfiguration(configuration);
        // Assert
        Assertions.assertTrue(result);
    }
}