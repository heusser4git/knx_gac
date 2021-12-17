package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.*;
import ch.ibw.knxgac.Repository.Database.Database;
import ch.ibw.knxgac.Repository.Database.SqlDatabase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;

public class Controller implements ControllerInterface {
    final String CONFIGFILE = System.getProperty("user.dir") + "/src/main/java/ch/ibw/knxgac/configuration.txt";
    private Database db;

    public Controller() throws IOException, ClassNotFoundException, SQLException {
        this.db = new SqlDatabase();
        this.starteDbConnection(this.getConfiguration());
        Path configfile = Path.of(CONFIGFILE);
        if(Files.notExists(configfile)) {
            // create Config File
            Files.createFile(configfile);
            // write a empty config into the file
            this.saveConfiguration(new Configuration());
        }
    }
    public Controller(Database db) throws IOException, SQLException, ClassNotFoundException {
        this();
        this.db = db;
    }

    @Override
    /**
     * saves the configuration to the Config File
     */
    public void saveConfiguration(Configuration configuration) throws IOException {
        File configfile = new File(CONFIGFILE);
        FileOutputStream f = new FileOutputStream(configfile);
        ObjectOutputStream o = new ObjectOutputStream(f);
        o.writeObject(configuration);
        o.close();
        f.close();
    }

    @Override
    /**
     * Opens the Config File and returns the Configuration
     */
    public Configuration getConfiguration() throws IOException, ClassNotFoundException {
        File configfile = new File(CONFIGFILE);
        FileInputStream fi = new FileInputStream(configfile);
        ObjectInputStream oi = new ObjectInputStream(fi);

        Configuration configuration = (Configuration) oi.readObject();

        oi.close();
        fi.close();

        return configuration;
    }
    private boolean starteDbConnection(Configuration configuration) throws SQLException {
        this.db.createConnection(configuration.getDbServertyp().name(), configuration.getDbServer(), configuration.getDbServerPort(), configuration.getDbName(), configuration.getDbUsername(), configuration.getDbPassword());
        return this.db.isConnected();
    }
    /**
     * Checks if the Configuration ist complete and the DB is reachable
     * @param configuration
     * @return true = all good, false not ok
     */
    @Override
    public boolean checkConfiguration(Configuration configuration) throws SQLException {
        // check the configuration-data
        if(configuration.configComplete()) {
            // try to connect to database
            boolean isConnected = this.starteDbConnection(configuration);

            // check if the CSV Outputpath exists
            Path path = Path.of(configuration.getCsvOutputpath());

            return (isConnected && Files.exists(path));
        }
        return false;
    }

    @Override
    public int insertObject(Data object) throws SQLException {
        if(object instanceof Project) {
            return this.db.insertProject((Project) object);
        } else if(object instanceof MainGroup) {
            return this.db.insertMaingroup((MainGroup) object);
        } else if(object instanceof MiddleGroup) {
            return this.db.insertMiddlegroup((MiddleGroup) object);
        } else if(object instanceof Address) {
            return this.db.insertAddress((Address) object);
        } else if(object instanceof ObjectTemplate) {
            return this.db.insertObjectTemplate((ObjectTemplate) object);
        } else if(object instanceof Attribute) {
            return this.db.insertAttribute((Attribute) object);
        }
        return 0;
    }

    @Override
    public <T> ArrayList<T> selectObject(T filter) throws SQLException {
        if(filter instanceof Project) {
            return (ArrayList<T>) this.db.selectProject((Project) filter);
        } else if(filter instanceof MainGroup) {
            return (ArrayList<T>) this.db.selectMaingroup((MainGroup) filter);
        } else if(filter instanceof MiddleGroup) {
            return (ArrayList<T>) this.db.selectMiddlegroup((MiddleGroup) filter);
        } else if(filter instanceof Address) {
            return (ArrayList<T>) this.db.selectAddress((Address) filter);
        } else if(filter instanceof ObjectTemplate) {
            return (ArrayList<T>) this.db.selectObjectTemplate((ObjectTemplate) filter);
        } else if(filter instanceof Attribute) {
            return (ArrayList<T>) this.db.selectAttribute((Attribute) filter);
        }
        return null;
    }

    @Override
    public void updateObject(Data object) throws SQLException {
        if(object instanceof Project) {
            this.db.updateProject((Project) object);
        } else if(object instanceof MainGroup) {
            this.db.updateMaingroup((MainGroup) object);
        } else if(object instanceof MiddleGroup) {
            this.db.updateMiddlegroup((MiddleGroup) object);
        } else if(object instanceof Address) {
            this.db.updateAddress((Address) object);
        } else if(object instanceof ObjectTemplate) {
            this.db.updateObjectTemplate((ObjectTemplate) object);
        } else if(object instanceof Attribute) {
            this.db.updateAttribute((Attribute) object);
        }
    }

    @Override
    public void deleteObject(Data object) throws SQLException {
        if(object instanceof Project) {
            this.db.deleteProject((Project) object);
        } else if(object instanceof MainGroup) {
            this.db.deleteMaingroup((MainGroup) object);
        } else if(object instanceof MiddleGroup) {
            this.db.deleteMiddlegroup((MiddleGroup) object);
        } else if(object instanceof Address) {
            this.db.deleteAddress((Address) object);
        } else if(object instanceof ObjectTemplate) {
            this.db.deleteObjectTemplate((ObjectTemplate) object);
        } else if(object instanceof Attribute) {
            this.db.deleteAttribute((Attribute) object);
        }
    }
}
