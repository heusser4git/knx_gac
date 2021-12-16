package ch.ibw.knxgac.Repository.Database;

import ch.ibw.knxgac.Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class SqlDatabase implements Database {
    private Connection connection;
    private String dburl;

    public Connection getConnection() {
        return connection;
    }

    /**
     * Creates a DB Connection
     *
     * @param databaseTyp String "mariadb" or "mysql"
     * @param database    String database name
     * @param user        String database-user
     * @param password    String database-password
     * @return boolean Returns a TRUE for positive Connection
     * @throws SQLException
     */
    public boolean createConnection(String databaseTyp, String dbServername, int dbServerPort, String database, String user, String password) throws SQLException {
        dburl = "jdbc:" + databaseTyp.toLowerCase(Locale.ROOT) + "://" + dbServername + ":" + dbServerPort + "/";
        this.connection = DriverManager.getConnection(dburl, user, password);
        this.createUseDatabase(database);
        return this.isConnected();
    }

    public boolean isConnected() throws SQLException {
        return this.connection.isValid(100);
    }

    public void closeConnection() throws SQLException {
        this.connection.close();
    }


    /**
     * Check if Database exists
     *
     * @param database
     */
    private boolean checkDatabase(String database) throws SQLException {
        String sql = "SELECT count(SCHEMA_NAME) as result FROM INFORMATION_SCHEMA.SCHEMATA WHERE SCHEMA_NAME = '" + database + "';";
        return this.dbAvailable(sql);
    }

    /**
     * Create the Database if not exists and USE it
     *
     * @param database
     */
    private void createUseDatabase(String database) throws SQLException {
        String sql;
        if (!checkDatabase(database)) {
            sql = "CREATE DATABASE " + database + ";";
            this.executeQuery(sql);
        }
        sql = "USE " + database + ";";
        this.executeQuery(sql);
        this.createTablesToDb();
    }

    /**
     * Executes the sql and checks the result-column "result" for an 1 or 0
     *
     * @param sql Sql-Query String for checking the DB with
     * @return true = is allready in DB
     * @throws SQLException
     */
    private boolean dbAvailable(String sql) throws SQLException {
        ResultSet resultSet = this.executeQuery(sql);
        resultSet.first();
        int count = Integer.parseInt(resultSet.getString("result"));
        if (count > 0)
            return true;
        return false;
    }

    /**
     * Runs the sql-query with statement.executeQuery
     *
     * @param sql
     * @return Returns the ResultSet
     * @throws SQLException
     */
    private ResultSet executeQuery(String sql) throws SQLException {
        Statement statement = this.connection.createStatement();
//        System.out.println(sql);
        return statement.executeQuery(sql);
    }

    /**
     * Gets the highest ID out of the table
     * @param table
     * @return
     * @throws SQLException
     */
    private int getMaxIdFromTable(String table) throws SQLException {
        String sql = "SELECT MAX(id) FROM " + table + ";";
        ResultSet resultSet = this.executeQuery(sql);
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }





    @Override
    public ArrayList<Project> selectProject(Project filter) throws SQLException {
        String sql = "SELECT * FROM Project WHERE " + filter.getWhereClause();
        ArrayList<Project> projects = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setName(resultSet.getString("name"));
            MainGroup filterMaingroup = new MainGroup();
            filterMaingroup.setIdProject(project.getId());
            project.setMaingroups(this.selectMaingroup(filterMaingroup));
            projects.add(project);
        }
        return projects;
    }

    @Override
    public int insertProject(Project object) throws SQLException {
        String sql = "INSERT INTO Project SET name='" + object.getName() + "';";
        this.executeQuery(sql);
        return this.getMaxIdFromTable("project");
    }

    @Override
    public boolean updateProject(Project object) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteProject(Project object) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<MainGroup> selectMaingroup(MainGroup filter) throws SQLException {
        String sql = "SELECT * FROM Maingroup WHERE " + filter.getWhereClause();
        ArrayList<MainGroup> maingroups = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            MainGroup mainGroup = new MainGroup();
            mainGroup.setId(resultSet.getInt("id"));
            mainGroup.setName(resultSet.getString("name"));
            mainGroup.setNumber(resultSet.getInt("number"));
            mainGroup.setIdProject(resultSet.getInt("idProject"));
            MiddleGroup filterMiddlegroup = new MiddleGroup();
            filterMiddlegroup.setIdMaingroup(mainGroup.getId());
            mainGroup.setMiddlegroups(this.selectMiddlegroup(filterMiddlegroup));
            maingroups.add(mainGroup);
        }
        return maingroups;
    }

    @Override
    public int insertMaingroup(MainGroup object) throws SQLException {
        String sql = "INSERT INTO Maingroup SET name='" + object.getName() + "', number=" + object.getNumber() + ", idProject=" + object.getIdProject() + ";";
        this.executeQuery(sql);
        return this.getMaxIdFromTable("Maingroup");
    }

    @Override
    public boolean updateMaingroup(MainGroup object) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteMaingroup(MainGroup object) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<MiddleGroup> selectMiddlegroup(MiddleGroup filter) throws SQLException {
        String sql = "SELECT * FROM Middlegroups WHERE " + filter.getWhereClause();
        ArrayList<MiddleGroup> middleGroups = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            MiddleGroup middleGroup = new MiddleGroup();
            middleGroup.setId(resultSet.getInt("id"));
            middleGroup.setName(resultSet.getString("name"));
            middleGroup.setNumber(resultSet.getInt("number"));
            middleGroup.setIdMaingroup(resultSet.getInt("idMaingroup"));
            Address filterAddress = new Address();
            filterAddress.setIdMiddlegroup(middleGroup.getId());
            middleGroup.setAddresses(this.selectAddress(filterAddress));
            middleGroups.add(middleGroup);
        }
        return middleGroups;
    }

    @Override
    public int insertMiddlegroup(MiddleGroup object) throws SQLException {
        String sql = "INSERT INTO Middlegroup SET name='" + object.getName() + "', number=" + object.getNumber() + ", idMaingroup=" + object.getIdMaingroup() + ";";
        this.executeQuery(sql);
        return this.getMaxIdFromTable("Middlegroup");
    }

    @Override
    public boolean updateMiddlegroup(MiddleGroup object) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteMiddlegroup(MiddleGroup object) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Address> selectAddress(Address filter) throws SQLException {
        String sql = "SELECT * FROM Address WHERE " + filter.getWhereClause();
        ArrayList<Address> addresses = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setName(resultSet.getString("name"));
            address.setIdMiddlegroup(resultSet.getInt("idMiddlegroup"));
            address.setStartAddress(resultSet.getInt("startaddress"));
            ObjectTemplate filterObjectTemplate = new ObjectTemplate();
            filterObjectTemplate.setIdAddress(address.getId());
            address.setObjectTemplates(this.selectObjectTemplate(filterObjectTemplate));
            addresses.add(address);
        }
        return addresses;
    }

    @Override
    public int insertAddress(Address object) throws SQLException {
        String sql = "INSERT INTO Address SET name='" + object.getName() + "', startaddress=" + object.getStartAddress() + ", idMiddlegroup=" + object.getIdMiddlegroup() + ";";
        this.executeQuery(sql);
        return this.getMaxIdFromTable("Address");
    }

    @Override
    public boolean updateAddress(Address object) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteAddress(Address object) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<ObjectTemplate> selectObjectTemplate(ObjectTemplate filter) throws SQLException {
        String sql = "SELECT * FROM ObjectTemplate WHERE " + filter.getWhereClause();
        ArrayList<ObjectTemplate> objectTemplates = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            ObjectTemplate objectTemplate = new ObjectTemplate();
            objectTemplate.setId(resultSet.getInt("id"));
            objectTemplate.setName(resultSet.getString("name"));
            Attribute filterAttribute = new Attribute();
            filterAttribute.setIdObjectTemplate(objectTemplate.getId());
            objectTemplate.setAttributes(this.selectAttribute(filterAttribute));
            objectTemplates.add(objectTemplate);
        }
        return objectTemplates;
    }

    @Override
    public int insertObjectTemplate(ObjectTemplate object) throws SQLException {
        String sql = "INSERT INTO ObjectTemplate SET name='" + object.getName() + "', idAddress="+ object.getIdAddress()+";";
        this.executeQuery(sql);
        return this.getMaxIdFromTable("ObjectTemplate");
    }

    @Override
    public boolean updateObjectTemplate(ObjectTemplate object) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteObjectTemplate(ObjectTemplate object) throws SQLException {
        return false;
    }

    @Override
    public ArrayList<Attribute> selectAttribute(Attribute filter) throws SQLException {
        String sql = "SELECT * FROM Attribute WHERE " + filter.getWhereClause();
        ArrayList<Attribute> attributes = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            Attribute attribute = new Attribute();
            attribute.setId(resultSet.getInt("id"));
            attribute.setName(resultSet.getString("name"));
            attribute.setIdObjectTemplate(resultSet.getInt("idObjectTemplate"));
            attributes.add(attribute);
        }
        return attributes;
    }

    @Override
    public int insertAttribute(Attribute object) throws SQLException {
        String sql = "INSERT INTO Attribute SET name='" + object.getName() + "', idObjectTemplate=" + object.getIdObjectTemplate() + ";";
        this.executeQuery(sql);
        return this.getMaxIdFromTable("Attribute");
    }

    @Override
    public boolean updateAttribute(Attribute object) throws SQLException {
        return false;
    }

    @Override
    public boolean deleteAttribute(Attribute object) throws SQLException {
        return false;
    }

    private String getSqlCreateTableProject() {
        return "CREATE TABLE IF NOT EXISTS `project` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(255) NULL,\n" +
                "  `deleted` TINYINT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (`id`));\n";
    }

    private String getSqlCreateTableMaingroup() {
        return "CREATE TABLE IF NOT EXISTS `maingroup` (\n" +
                "  `id` INT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(255) NULL,\n" +
                "  `number` INT(11) NULL,\n" +
                "  `idProject` INT NOT NULL,\n" +
                "  `deleted` TINYINT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (`id`),\n" +
                "  INDEX `fk_maingroup_project1_idx` (`idProject` ASC) VISIBLE,\n" +
                "  CONSTRAINT `fk_maingroup_project1`\n" +
                "    FOREIGN KEY (`idProject`)\n" +
                "    REFERENCES `project` (`id`)\n" +
                ");\n";
    }

    private String getSqlCreateTableMiddlegroup() {
        return "CREATE TABLE IF NOT EXISTS `middlegroup` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(255) NULL," +
                "  `number` INT(11) NULL," +
                "  `idMaingroup` INT NOT NULL," +
                "  `deleted` TINYINT NULL DEFAULT 0," +
                "  PRIMARY KEY (`id`)," +
                "  INDEX `fk_middlegroup_maingroup1_idx` (`idMaingroup` ASC) VISIBLE," +
                "  CONSTRAINT `fk_middlegroup_maingroup1`" +
                "    FOREIGN KEY (`idMaingroup`)" +
                "    REFERENCES `maingroup` (`id`)" +
                ");";
    }

    private String getSqlCreateTableAddress() {
        return "CREATE TABLE IF NOT EXISTS `address` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(255) NULL," +
                "  `startaddress` INT(11) NULL," +
                "  `idMiddlegroup` INT NOT NULL," +
                "  `deleted` TINYINT NULL DEFAULT 0," +
                "  PRIMARY KEY (`id`)," +
                "  INDEX `fk_address_middlegroup1_idx` (`idMiddlegroup` ASC) VISIBLE," +
                "  CONSTRAINT `fk_address_middlegroup1`" +
                "    FOREIGN KEY (`idMiddlegroup`)" +
                "    REFERENCES `middlegroup` (`id`)" +
                ");";
    }

    private String getSqlCreateTableObjectTemplate() {
        return "CREATE TABLE IF NOT EXISTS `objecttemplate` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(255) NULL," +
                "  `idAddress` INT NOT NULL," +
                "  `deleted` TINYINT NULL DEFAULT 0," +
                "  PRIMARY KEY (`id`)," +
                "  INDEX `fk_objecttemplate_address1_idx` (`idAddress` ASC) VISIBLE," +
                "  CONSTRAINT `fk_objecttemplate_address1`" +
                "    FOREIGN KEY (`idAddress`)" +
                "    REFERENCES `address` (`id`)" +
                ");";
    }

    private String getSqlCreateTableAttribute() {
        return "CREATE TABLE IF NOT EXISTS `attribute` (" +
                "  `id` INT NOT NULL AUTO_INCREMENT," +
                "  `name` VARCHAR(255) NULL," +
                "  `idObjectTemplate` INT NOT NULL," +
                "  `deleted` TINYINT NULL DEFAULT 0," +
                "  PRIMARY KEY (`id`)," +
                "  INDEX `fk_attribute_objecttemplate_idx` (`idObjectTemplate` ASC) VISIBLE," +
                "  CONSTRAINT `fk_attribute_objecttemplate`" +
                "    FOREIGN KEY (`idObjectTemplate`)" +
                "    REFERENCES `objecttemplate` (`id`)" +
                "    );";
    }

    public void createTablesToDb() throws SQLException {
        this.executeQuery(this.getSqlCreateTableProject());
        this.executeQuery(this.getSqlCreateTableMaingroup());
        this.executeQuery(this.getSqlCreateTableMiddlegroup());
        this.executeQuery(this.getSqlCreateTableAddress());
        this.executeQuery(this.getSqlCreateTableObjectTemplate());
        this.executeQuery(this.getSqlCreateTableAttribute());
    }

}