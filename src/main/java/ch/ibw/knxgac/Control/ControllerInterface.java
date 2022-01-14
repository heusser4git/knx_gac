package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.Configuration;
import ch.ibw.knxgac.Model.Data;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Dieser Controller soll als Schnittstelle
 * zwischen GUI und Datenhaltung
 */
public interface ControllerInterface  {
    /**
     * Saves the Configuration
     */
    void saveConfiguration(Configuration configuration) throws IOException;

    /**
     * Gets the Configuration
     * @return Configuration
     */
    Configuration getConfiguration() throws IOException;

    /**
     * Checks if the Configuration ist complete and the DB is reachable
     * @return true = all good, false not ok
     */
    boolean checkConfiguration(Configuration configuration) throws SQLException;

    int insertObject(Data object) throws SQLException;
    <T>  ArrayList<T> selectObject(T filter) throws SQLException;
    void updateObject(Data object) throws SQLException;
    void deleteObject(Data object) throws SQLException;
}
