package ch.ibw.knxgac.Model.Repository;

import ch.ibw.knxgac.Model.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Locale;

public class SqlDatabase implements Database {
    private Connection connection;
    private String dburl;


    /**
     * Creates a DB Connection
     *
     * @param configuration Object Configuration
     * @return boolean Returns a TRUE for positive Connection
     * @throws SQLException
     */
    public boolean createConnection(Configuration configuration) throws SQLException {
        dburl = "jdbc:" + configuration.getDbServertyp().name().toLowerCase(Locale.ROOT) + "://" + configuration.getDbServer() + ":" + configuration.getDbServerPort() + "/";
        this.connection = DriverManager.getConnection(dburl, configuration.getDbUsername(), configuration.getDbPassword());
        this.createUseDatabase(configuration.getDbName());
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
     * Gets the highest ID out of the table and adds 1
     * @param table
     * @return
     * @throws SQLException
     */
    private int getNextIdFromTable(String table) throws SQLException {
        String sql = "SELECT MAX(id) AS id FROM " + table + ";";
        ResultSet resultSet = this.executeQuery(sql);
        if (resultSet.next()) {
            return (resultSet.getInt("id")+1);
        }
        throw new SQLException("Next ID could not be found!");
    }



    /**
     * Generates the SQL-Query-String for Select Data Operation
     * @param table         String Name of the Table
     * @param filter        Data    Object which filters and orders the select-query
     * @return              String The whole Query to retrieve Data out of the Table
     */
    private String getSqlSelectQuery(String table, Data filter) {
        return "SELECT * FROM " + table + " WHERE " + filter.getWhereClause() + " ORDER BY " + filter.getOrderByClause();
    }

    /**
     * Generates the SQL-Query-String for Insert Data Operation
     * @param table         String Name of the Table
     * @param fieldsValues  Fields and Values for the SET Part of the SQL-Query, eg. "name='jack', number=123"
     * @return
     */
    protected String getSqlInsertQuery(String table, String fieldsValues) {
        return "INSERT INTO " + table + " SET " + fieldsValues + ";";
    }

    protected String getSqlUpdateQuery(String table, Data object) {
        return "UPDATE " + table + " SET " + object.getUpdateClause() + " WHERE id=" + object.getId() + ";";
    }
    /**
     * Generates the SQL-Query-String for Insert Data Operation
     * @param table    String Name of the Table
     * @param id       int  ID of the dataset which has to be deleted
     * @return
     */
    protected String getSqlDeleteQuery(String table, int id) {
        return "UPDATE " + table + " SET deleted=1 WHERE id=" + id + ";";
    }



    @Override
    public ArrayList<Project> selectProject(Project filter) throws SQLException {
        String sql = this.getSqlSelectQuery("Project", filter);
        ArrayList<Project> projects = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            Project project = new Project();
            project.setId(resultSet.getInt("id"));
            project.setName(resultSet.getString("name"));
            project.setNumber(resultSet.getInt("number"));
            MainGroup filterMaingroup = new MainGroup();
            filterMaingroup.setIdProject(project.getId());
            project.setMaingroups(this.selectMaingroup(filterMaingroup));
            projects.add(project);
        }
        return projects;
    }

    /**
     * Inserts a new Project-Row
     * @param object    Project with data to be written into DB
     * @return          ID of the written Project-Row
     * @throws SQLException
     */
    @Override
    public int insertProject(Project object) throws SQLException {
        int idProject = this.getNextIdFromTable("Project");
        String sql = this.getSqlInsertQuery("Project", "id=" + idProject + ", name='" + object.getName() + "', number=" + object.getNumber());
        this.executeQuery(sql);
        return idProject;
    }

    @Override
    public void updateProject(Project object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlUpdateQuery("Project", object));
    }

    /**
     * Deletes a row off Project by writing 1 to the deleted-Flag
     * @param object    Project with an ID is required
     * @throws SQLException
     */
    @Override
    public void deleteProject(Project object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlDeleteQuery("Project", object.getId()));
    }

    @Override
    public ArrayList<MainGroup> selectMaingroup(MainGroup filter) throws SQLException {
        String sql = this.getSqlSelectQuery("Maingroup", filter);
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
        int idMaingroup = this.getNextIdFromTable("Maingroup");
        String sql = this.getSqlInsertQuery("Maingroup", "id=" + idMaingroup + ", name='" + object.getName() + "', number=" + object.getNumber() + ", idProject=" + object.getIdProject());
        this.executeQuery(sql);
        return idMaingroup;
    }

    @Override
    public void updateMaingroup(MainGroup object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlUpdateQuery("Maingroup", object));
    }

    @Override
    public void deleteMaingroup(MainGroup object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlDeleteQuery("Maingroup", object.getId()));
    }

    @Override
    public ArrayList<MiddleGroup> selectMiddlegroup(MiddleGroup filter) throws SQLException {
        String sql = this.getSqlSelectQuery("Middlegroup", filter);
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
        int idMiddlegroup = this.getNextIdFromTable("Middlegroup");
        String sql = this.getSqlInsertQuery("Middlegroup", "id=" + idMiddlegroup + ", name='" + object.getName() + "', number=" + object.getNumber() + ", idMaingroup=" + object.getIdMaingroup());
        this.executeQuery(sql);
        return idMiddlegroup;
    }

    @Override
    public void updateMiddlegroup(MiddleGroup object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlUpdateQuery("Middlegroup", object));
    }

    @Override
    public void deleteMiddlegroup(MiddleGroup object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlDeleteQuery("Middlegroup", object.getId()));
    }

    @Override
    public ArrayList<Address> selectAddress(Address filter) throws SQLException {
        String sql = this.getSqlSelectQuery("Address", filter);
        ArrayList<Address> addresses = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            Address address = new Address();
            address.setId(resultSet.getInt("id"));
            address.setName(resultSet.getString("name"));
            address.setIdMiddlegroup(resultSet.getInt("idMiddlegroup"));
            address.setStartAddress(resultSet.getInt("startaddress"));
            ObjectTemplate filterObjectTemplate = new ObjectTemplate();
            filterObjectTemplate.setId(resultSet.getInt("idObjecttemplate"));
            ArrayList<ObjectTemplate> objectTemplates = this.selectObjectTemplate(filterObjectTemplate);
            if(objectTemplates.size()==1) {
                address.setObjectTemplate(objectTemplates.get(0));
            }
            addresses.add(address);
        }
        return addresses;
    }

    @Override
    public int insertAddress(Address object) throws SQLException {
        int idAddress = this.getNextIdFromTable("Address");
        String objectTemplate = "";
        if(object.getObjectTemplate()!=null && object.getObjectTemplate().getId()>0) {
            objectTemplate = ", idObjectTemplate=" + object.getObjectTemplate().getId();
        }
        String sql = this.getSqlInsertQuery("Address", "id=" + idAddress + ", name='" + object.getName() + "', startaddress=" + object.getStartAddress() + ", idMiddlegroup=" + object.getIdMiddlegroup() + objectTemplate);
        this.executeQuery(sql);
        return idAddress;
    }

    @Override
    public void updateAddress(Address object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlUpdateQuery("Address", object));
    }

    @Override
    public void deleteAddress(Address object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlDeleteQuery("Address", object.getId()));
    }

    @Override
    public ArrayList<ObjectTemplate> selectObjectTemplate(ObjectTemplate filter) throws SQLException {
        String sql = this.getSqlSelectQuery("ObjectTemplate", filter);
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
        int idObjectTemplate = this.getNextIdFromTable("ObjectTemplate");
        String sql = this.getSqlInsertQuery("ObjectTemplate", "id=" + idObjectTemplate + ", name='" + object.getName() + "'");
        this.executeQuery(sql);
        return idObjectTemplate;
    }

    @Override
    public void updateObjectTemplate(ObjectTemplate object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlUpdateQuery("ObjectTemplate", object));
    }

    @Override
    public void deleteObjectTemplate(ObjectTemplate object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlDeleteQuery("ObjectTemplate", object.getId()));
    }

    @Override
    public ArrayList<Attribute> selectAttribute(Attribute filter) throws SQLException {
        String sql = this.getSqlSelectQuery("Attribute", filter);
        ArrayList<Attribute> attributes = new ArrayList<>();
        ResultSet resultSet = this.executeQuery(sql);
        while (resultSet.next()) {
            Attribute attribute = new Attribute();
            attribute.setId(resultSet.getInt("id"));
            attribute.setName(resultSet.getString("name"));
            attribute.setNumber(resultSet.getInt("number"));
            attribute.setIdObjectTemplate(resultSet.getInt("idObjectTemplate"));
            attributes.add(attribute);
        }
        return attributes;
    }

    @Override
    public int insertAttribute(Attribute object) throws SQLException {
        int idAttribute = this.getNextIdFromTable("Attribute");
        String sql = this.getSqlInsertQuery("Attribute", "id=" + idAttribute + ", name='" + object.getName() + "', number=" + object.getNumber() + ", idObjectTemplate=" + object.getIdObjectTemplate());
        this.executeQuery(sql);
        return idAttribute;
    }

    @Override
    public void updateAttribute(Attribute object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlUpdateQuery("Attribute", object));
    }



    @Override
    public void deleteAttribute(Attribute object) throws SQLException {
        if(object.getId()>0)
            this.executeQuery(this.getSqlDeleteQuery("Attribute", object.getId()));
    }

    private String getSqlCreateTableProject() {
        return "CREATE TABLE IF NOT EXISTS `project` (\n" +
                "  `id` INT NOT NULL,\n" +
                "  `name` VARCHAR(255) NULL,\n" +
                "  `number` INT(11) NULL,\n" +
                "  `deleted` TINYINT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (`id`));\n";
    }

    private String getSqlCreateTableMaingroup() {
        return "CREATE TABLE IF NOT EXISTS `maingroup` (\n" +
                "  `id` INT NOT NULL,\n" +
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
                "  `id` INT NOT NULL," +
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
        return "CREATE TABLE IF NOT EXISTS address (\n" +
                "  id INT NOT NULL,\n" +
                "  name VARCHAR(255) NULL,\n" +
                "  startaddress INT(11) NULL,\n" +
                "  idMiddlegroup INT NOT NULL,\n" +
                "  idObjecttemplate INT DEFAULT 0,\n" +
                "  deleted TINYINT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (id, idObjecttemplate),\n" +
                "  INDEX fk_address_middlegroup1_idx (idMiddlegroup ASC) VISIBLE,\n" +
                "  INDEX fk_address_objecttemplate1_idx (idObjecttemplate ASC) VISIBLE,\n" +
                "  CONSTRAINT fk_address_middlegroup1\n" +
                "    FOREIGN KEY (idMiddlegroup)\n" +
                "    REFERENCES middlegroup (id),\n" +
                "  CONSTRAINT fk_address_objecttemplate1\n" +
                "    FOREIGN KEY (idObjecttemplate)\n" +
                "    REFERENCES objecttemplate (id)\n" +
                ");";
    }

    private String getSqlCreateTableObjectTemplate() {
        return "CREATE TABLE IF NOT EXISTS objecttemplate (\n" +
                "  id INT NOT NULL,\n" +
                "  name VARCHAR(255) NULL,\n" +
                "  deleted TINYINT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (id));";
    }

    private String getSqlCreateTableAttribute() {
        return "CREATE TABLE IF NOT EXISTS attribute (\n" +
                "  id INT NOT NULL,\n" +
                "  name VARCHAR(255) NULL,\n" +
                "  number INT NULL,\n" +
                "  idObjectTemplate INT NOT NULL,\n" +
                "  deleted TINYINT NULL DEFAULT 0,\n" +
                "  PRIMARY KEY (id),\n" +
                "  INDEX fk_attribute_objecttemplate_idx (idObjectTemplate ASC) VISIBLE,\n" +
                "  CONSTRAINT fk_attribute_objecttemplate\n" +
                "    FOREIGN KEY (idObjectTemplate)\n" +
                "    REFERENCES objecttemplate (id)\n" +
                ");";
    }

    /**
     * As long as the GUI is not implemented for
     * Managing the ObjectTemplates, insert them
     * automatically into the Database
     */
    private String getSqlInsertObjectTemplates() {
        return "INSERT INTO objecttemplate (id, name) \n" +
                "VALUES \n" +
                "(1, 'Licht Dimmen'),\n" +
                "(2, 'Licht Ein/Aus'),\n" +
                "(3, 'Jalousie'),\n" +
                "(4, 'Heizen');";
    }
    /**
     * As long as the GUI is not implemented for
     * Managing the ObjectTemplate-Attributes, insert them
     * automatically into the Database
     */
    private String getSqlInsertAttributes() {
        return "INSERT INTO attribute (id, name, number, idObjectTemplate) \n" +
                "VALUES \n" +
                "(1, 'E/A', 0, 1),\n" +
                "(2, 'H/D', 1, 1),\n" +
                "(3, 'Wert %', 2, 1),\n" +
                "(4, 'RM', 3, 1),\n" +
                "(5, 'RM Wert %', 4, 1),\n" +
                "(6, 'E/A', 0, 2),\n" +
                "(7, 'RM', 1, 2),\n" +
                "(8, '-', 2, 2),\n" +
                "(9, '-', 3, 2),\n" +
                "(10, '-', 4, 2),\n" +
                "(11, 'Auf/Zu', 0, 3),\n" +
                "(12, 'Auf/Ab', 1, 3),\n" +
                "(13, 'Position Jalousie', 2, 3),\n" +
                "(14, 'Position Lamellen', 3, 3),\n" +
                "(15, 'Sperren', 4, 3),\n" +
                "(16, 'Status Jalousie', 5, 3),\n" +
                "(17, 'Status Lamellen', 6, 3),\n" +
                "(18, 'Stellgr??sse', 0, 4),\n" +
                "(19, 'RM Ist Wert', 1, 4),\n" +
                "(20, 'Basis-Soll', 2, 4),\n" +
                "(21, 'RM Soll Wert', 3, 4),\n" +
                "(22, 'Betriebsart', 4, 4),\n" +
                "(23, 'Sperren', 5, 4);";
    }

    public void createTablesToDb() throws SQLException {
        this.executeQuery(this.getSqlCreateTableProject());
        this.executeQuery(this.getSqlCreateTableMaingroup());
        this.executeQuery(this.getSqlCreateTableMiddlegroup());
        this.executeQuery(this.getSqlCreateTableObjectTemplate());
        this.executeQuery(this.getSqlCreateTableAttribute());
        this.executeQuery(this.getSqlCreateTableAddress());

        // Insert Data for ObjectTemplates and Attributes
        if(this.getNextIdFromTable("ObjectTemplate")==1 && this.getNextIdFromTable("Attribute")==1) {
            this.executeQuery(this.getSqlInsertObjectTemplates());
            this.executeQuery(this.getSqlInsertAttributes());
        }
    }

}