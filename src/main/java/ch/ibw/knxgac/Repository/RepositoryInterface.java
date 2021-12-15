package ch.ibw.knxgac.Repository;

import ch.ibw.knxgac.Model.*;

import java.util.ArrayList;

public interface RepositoryInterface<T extends DataInterface> {
    /**
     * Gets Data of the given Filter-Object which extends DataInterface
     * Expl. If the Filter-Object has an ID the Return will be an ArrayList with
     * the Object with the given ID. If only "Name" is given in Filter, all Data
     * with matching "Name" are returned in the ArrayList.
     * If the Filter-Object ist empty, the result contains any Data of this type.
     *
     * @param filter    of any Objecttypes
     * @return ArrayList with all matching Objects
     */
    ArrayList<T> getData(T filter);

    /**
     * Saves the Data of the Object which is given.
     * Returns the Object completed with the generated ID,
     * if ID ist given, an update is made.
     * @param object
     * @return T      Returns the Object completed with the generated ID
     */
    T saveData(T object);

    /**
     * Deletes the given Object.
     * The ID of the Object has to be given.
     * @param object    Object to delete
     * @return true if its deleted, otherwise false
     */
    boolean deleteData(T object);

    /**
     * Deletes all Data of the Objects in ArrayList.
     * Each Object has to have an ID.
     * @param objectsToDelete   ArrayList of Objects to delete
     * @return true if its deleted, otherwise false
     */
    boolean deleteData(ArrayList<T> objectsToDelete);

    /**
     *
     * @param object
     * @return
     */
    boolean saveConfig(ConfigurationInterface object);
    ConfigurationInterface getConfiguration();
}
