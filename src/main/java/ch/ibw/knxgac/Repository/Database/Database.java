package ch.ibw.knxgac.Repository.Database;

import ch.ibw.knxgac.Model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Database {
    boolean createConnection(String databaseTyp, String dbServer, int dbServerPort, String database, String user, String password) throws SQLException;
    boolean isConnected() throws SQLException;
    void closeConnection() throws SQLException;

    public ArrayList<Project> selectProject(Project filter) throws SQLException;
    public boolean insertProject(Project object) throws SQLException;
    public boolean updateProject(Project object) throws SQLException;
    public boolean deleteProject(Project object) throws SQLException;

    public ArrayList<MainGroup> selectMaingroup(MainGroup filter) throws SQLException;
    public boolean insertMaingroup(MainGroup object) throws SQLException;
    public boolean updateMaingroup(MainGroup object) throws SQLException;
    public boolean deleteMaingroup(MainGroup object) throws SQLException;

    public ArrayList<MiddleGroup> selectMiddlegroup(MiddleGroup filter) throws SQLException;
    public boolean insertMiddlegroup(MiddleGroup object) throws SQLException;
    public boolean updateMiddlegroup(MiddleGroup object) throws SQLException;
    public boolean deleteMiddlegroup(MiddleGroup object) throws SQLException;

    public ArrayList<Address> selectAddress(Address filter) throws SQLException;
    public boolean insertAddress(Address object) throws SQLException;
    public boolean updateAddress(Address object) throws SQLException;
    public boolean deleteAddress(Address object) throws SQLException;

    public ArrayList<ObjectTemplate> selectObjectTemplate(ObjectTemplate filter) throws SQLException;
    public boolean insertObjectTemplate(ObjectTemplate object) throws SQLException;
    public boolean updateObjectTemplate(ObjectTemplate object) throws SQLException;
    public boolean deleteObjectTemplate(ObjectTemplate object) throws SQLException;

    public ArrayList<Attribute> selectAttribute(Attribute filter) throws SQLException;
    public boolean insertAttribute(Attribute object) throws SQLException;
    public boolean updateAttribute(Attribute object) throws SQLException;
    public boolean deleteAttribute(Attribute object) throws SQLException;

    void createTablesToDb() throws SQLException;
}
