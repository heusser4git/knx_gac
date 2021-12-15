package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.Configuration;

import java.io.IOException;
import java.sql.SQLException;

/**
 * Dieser Controller soll als Schnittstelle
 * zwischen GUI und Datenhaltung
 */
public interface ControllerInterface  {
    /**
     * Saves the Configuration
     * @param configuration
     * @throws IOException
     */
    public void saveConfiguration(Configuration configuration) throws IOException;

    /**
     * Gets the Configuration
     * @return Configuration
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Configuration getConfiguration() throws IOException, ClassNotFoundException;

    /**
     * Checks if the Configuration ist complete and the DB is reachable
     * @param configuration
     * @return true = all good, false not ok
     */
    public boolean checkConfiguration(Configuration configuration) throws SQLException;
}
