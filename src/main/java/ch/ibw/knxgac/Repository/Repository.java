package ch.ibw.knxgac.Repository;

import ch.ibw.knxgac.Model.*;
import ch.ibw.knxgac.Repository.Database.Database;
import ch.ibw.knxgac.Repository.Database.Sql;

import java.sql.SQLException;
import java.util.ArrayList;

public class Repository implements RepositoryInterface {
    Database db = new Sql();
    public Repository() throws SQLException {
        // TODO korrekte connection
        //db.createConnection("mariadb", "semesterprojekt", "root", "123456");

    }


    @Override
    public ArrayList getData(DataInterface filter) {
        if(filter.getClass().getSimpleName().equals(new String("Project"))) {
            ArrayList<Project> result = new ArrayList();
            try {
                db.select(filter);
            } catch (SQLException e) {
                // TODO Errormeldung
                e.printStackTrace();
            } catch (Exception e) {
                // TODO Errormeldung -> allenfalls auch noch andere Exceptiontypen behandeln
                // NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException
                e.printStackTrace();
            }
            result.add((Project) filter);
            return result;
        } else if(filter.getClass().getSimpleName().equals(new String("Address"))) {
            ArrayList<Address> result = new ArrayList();
            result.add((Address) filter);
            return result;
        } else if(filter.getClass().getSimpleName().equals(new String("Floor"))) {
            ArrayList<MainGroup> result = new ArrayList();
            result.add((MainGroup) filter);
            return result;
        } else if(filter.getClass().getSimpleName().equals(new String("MiddleGroup"))) {
            ArrayList<MiddleGroup> result = new ArrayList();
            result.add((MiddleGroup) filter);
            return result;
        }
        return null;
    }

    @Override
    public DataInterface saveData(DataInterface object) {
        return null;
    }

    @Override
    public boolean deleteData(DataInterface object) {
        return false;
    }

    @Override
    public boolean deleteData(ArrayList objectsToDelete) {
        return false;
    }

    @Override
    public boolean saveConfig(ConfigurationInterface object) {
        return false;
    }

    @Override
    public ConfigurationInterface getConfiguration() {
        return null;
    }
}
