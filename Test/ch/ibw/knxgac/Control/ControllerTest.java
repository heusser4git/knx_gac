package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.Configuration;
import ch.ibw.knxgac.Repository.Database;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Iterables;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.sql.SQLException;

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
    void checkConfiguration() throws SQLException, IOException {
        // Arrange
        Database db = mock(Database.class);
        Configuration configuration = mock(Configuration.class);
        // TODO CSV Output is null so it crashes at Controller:115
        configuration.setCsvOutputpath(Iterables.firstOf(FileSystems.getDefault().getRootDirectories()).toString());
        Controller controller = new Controller(db);

        doReturn(true).when(configuration).configComplete();
        doReturn(true).when(db).isConnected();

        // Act
        boolean result = controller.checkConfiguration(configuration);
        // Assert
        Assertions.assertTrue(result);
    }
}