package ch.ibw.knxgac.Repository;

import ch.ibw.knxgac.Model.*;
import ch.ibw.knxgac.Repository.Database.Database;
import ch.ibw.knxgac.Repository.Database.SqlDatabase;

import java.sql.SQLException;
import java.util.ArrayList;

public class Repository implements RepositoryInterface {
    Database db = new SqlDatabase();
    public Repository() throws SQLException {
        // TODO korrekte connection
        //db.createConnection("mariadb", "semesterprojekt", "root", "123456");

    }


    @Override
    public ArrayList getData(Data filter) {
        if(filter.getClass().getSimpleName().equals(new String("Project"))) {
            ArrayList<Project> result = new ArrayList();
            try {
                this.db.selectProject((Project) filter);
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
    public Data saveData(Data object) {
        if(object.getId()>0) {
            // update
            if(object instanceof Project) {

            } else if(object instanceof MainGroup) {

            } else if(object instanceof MiddleGroup) {

            } else if(object instanceof Address) {

            } else if(object instanceof ObjectTemplate) {

            } else if(object instanceof Attribute) {

            }

        } else {
            // insert
            if(object instanceof Project) {

            } else if(object instanceof MainGroup) {

            } else if(object instanceof MiddleGroup) {

            } else if(object instanceof Address) {

            } else if(object instanceof ObjectTemplate) {

            } else if(object instanceof Attribute) {

            }
        }
        return null;
    }
    private void createProject(Project project) {

    }

    @Override
    public boolean deleteData(Data object) {
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
