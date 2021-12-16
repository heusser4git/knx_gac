package ch.ibw.knxgac.Repository.Database;

import ch.ibw.knxgac.Model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Database {
    boolean createConnection(String databaseTyp, String dbServer, int dbServerPort, String database, String user, String password) throws SQLException;
    boolean isConnected() throws SQLException;
    void closeConnection() throws SQLException;

    ArrayList<Project> selectProject(Project filter) throws SQLException;
    int insertProject(Project object) throws SQLException;
    boolean updateProject(Project object) throws SQLException;
    boolean deleteProject(Project object) throws SQLException;

    ArrayList<MainGroup> selectMaingroup(MainGroup filter) throws SQLException;
    int insertMaingroup(MainGroup object) throws SQLException;
    boolean updateMaingroup(MainGroup object) throws SQLException;
    boolean deleteMaingroup(MainGroup object) throws SQLException;

    ArrayList<MiddleGroup> selectMiddlegroup(MiddleGroup filter) throws SQLException;
    int insertMiddlegroup(MiddleGroup object) throws SQLException;
    boolean updateMiddlegroup(MiddleGroup object) throws SQLException;
    boolean deleteMiddlegroup(MiddleGroup object) throws SQLException;

    ArrayList<Address> selectAddress(Address filter) throws SQLException;
    int insertAddress(Address object) throws SQLException;
    boolean updateAddress(Address object) throws SQLException;
    boolean deleteAddress(Address object) throws SQLException;

    ArrayList<ObjectTemplate> selectObjectTemplate(ObjectTemplate filter) throws SQLException;
    int insertObjectTemplate(ObjectTemplate object) throws SQLException;
    boolean updateObjectTemplate(ObjectTemplate object) throws SQLException;
    boolean deleteObjectTemplate(ObjectTemplate object) throws SQLException;

    ArrayList<Attribute> selectAttribute(Attribute filter) throws SQLException;
    int insertAttribute(Attribute object) throws SQLException;
    boolean updateAttribute(Attribute object) throws SQLException;
    boolean deleteAttribute(Attribute object) throws SQLException;

    void createTablesToDb() throws SQLException;
}
