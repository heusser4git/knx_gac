package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.Configuration;
import ch.ibw.knxgac.Model.Project;
import ch.ibw.knxgac.Repository.Database.Database;
import ch.ibw.knxgac.Repository.Database.SqlDatabase;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

public class Controller implements ControllerInterface {
    final String CONFIGFILE = System.getProperty("user.dir") + "/src/main/java/ch/ibw/knxgac/configuration.txt";
    private Database db;

    public Controller() throws IOException {
        this.db = new SqlDatabase();
        Path configfile = Path.of(CONFIGFILE);
        if(Files.notExists(configfile)) {
            // create Config File
            Files.createFile(configfile);
            // write a empty config into the file
            this.saveConfiguration(new Configuration());
        }
    }
    public Controller(Database db) throws IOException {
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
            this.db.createConnection(configuration.getDbServertyp().name(), configuration.getDbServer(), configuration.getDbServerPort(), configuration.getDbName(), configuration.getDbUsername(), configuration.getDbPassword());
            boolean isConnected = this.db.isConnected();
            this.db.closeConnection();

            // check if the CSV Outputpath exists
            Path path = Path.of(configuration.getCsvOutputpath());

            return (isConnected && Files.exists(path));
        }
        return false;
    }

    public boolean createProject(Project project) {
return true;
    }
}
