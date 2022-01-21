package ch.ibw.knxgac.Control;

import ch.ibw.knxgac.Model.*;
import ch.ibw.knxgac.Model.Repository.Database;
import ch.ibw.knxgac.Model.Repository.SqlDatabase;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Controller implements ControllerInterface {
    final String CONFIGFILE = "configuration.txt";
//    final String CONFIGFILE = System.getProperty("user.dir") + "/src/main/java/ch/ibw/knxgac/configuration.txt";
    private Database db;

    public Controller() throws IOException, SQLException {
        this.createEmptyConfig();
        this.db = new SqlDatabase();
        Configuration configuration = this.getConfiguration();
        if(configuration.configComplete()) {
            this.starteDbConnection(configuration);
        }
    }

    public Controller(Database db) throws IOException, SQLException {
        this();
        this.db = db;
    }

    private void createEmptyConfig() throws IOException {
        Path configfile = Path.of(CONFIGFILE);
        if(Files.notExists(configfile)) {
            // create Config File
            Files.createFile(configfile);
            // write a empty config into the file
            Configuration c = new Configuration();
            this.saveConfiguration(c);
        }
    }

    /**
     * Saves the Configuration
     *
     */
    @Override
    public void saveConfiguration(Configuration configuration) throws IOException {
        List<String> lines = new ArrayList<>();
        lines.add(configuration.getDbServer());
        lines.add(configuration.getDbServertyp().name());
        lines.add(String.valueOf(configuration.getDbServerPort()));
        lines.add(configuration.getDbName());
        lines.add(configuration.getDbUsername());
        lines.add(configuration.getDbPassword());
        lines.add(configuration.getCsvOutputpath());

        Path pathWrite = Path.of(CONFIGFILE);
        Files.write(pathWrite, lines, StandardCharsets.UTF_8);
    }

    /**
     * Gets the Configuration
     *
     * @return Configuration
     */
    @Override
    public Configuration getConfiguration() throws IOException {
        this.createEmptyConfig();

        Path path = Path.of(CONFIGFILE);
        List<String> lines = Files.readAllLines(path);
        if(lines.size()>5) {
            Configuration configuration = new Configuration();
            configuration.setDbServer(lines.get(0));
            for(Servertyp s : Servertyp.values()) {
                if(lines.get(1).equals(s.name())) {
                    configuration.setDbServertyp(s);
                    break;
                }
            }
            configuration.setDbServerPort(Integer.parseInt(lines.get(2)));
            configuration.setDbName(lines.get(3));
            configuration.setDbUsername(lines.get(4));
            configuration.setDbPassword(lines.get(5));
            configuration.setCsvOutputpath(lines.get(6));
            return configuration;
        } else {
            throw new IOException("configuration.txt File nicht vollständig.");
        }
    }

    private boolean starteDbConnection(Configuration configuration) throws SQLException {
        this.db.createConnection(configuration);
        return this.db.isConnected();
    }
    /**
     * Checks if the Configuration ist complete and the DB is reachable
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
    public <T extends Data> ArrayList<T> selectObject(T filter) throws SQLException {
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
