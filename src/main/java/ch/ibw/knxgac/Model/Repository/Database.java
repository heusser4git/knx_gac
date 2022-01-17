package ch.ibw.knxgac.Model.Repository;

import ch.ibw.knxgac.Model.*;

import java.sql.SQLException;
import java.util.ArrayList;

public interface Database {
    boolean createConnection(Configuration configuration) throws SQLException;
    boolean isConnected() throws SQLException;
    void closeConnection() throws SQLException;

    ArrayList<Project> selectProject(Project filter) throws SQLException;
    int insertProject(Project object) throws SQLException;
    void updateProject(Project object) throws SQLException;
    void deleteProject(Project object) throws SQLException;

    ArrayList<MainGroup> selectMaingroup(MainGroup filter) throws SQLException;
    int insertMaingroup(MainGroup object) throws SQLException;
    void updateMaingroup(MainGroup object) throws SQLException;
    void deleteMaingroup(MainGroup object) throws SQLException;

    ArrayList<MiddleGroup> selectMiddlegroup(MiddleGroup filter) throws SQLException;
    int insertMiddlegroup(MiddleGroup object) throws SQLException;
    void updateMiddlegroup(MiddleGroup object) throws SQLException;
    void deleteMiddlegroup(MiddleGroup object) throws SQLException;

    ArrayList<Address> selectAddress(Address filter) throws SQLException;
    int insertAddress(Address object) throws SQLException;
    void updateAddress(Address object) throws SQLException;
    void deleteAddress(Address object) throws SQLException;

    ArrayList<ObjectTemplate> selectObjectTemplate(ObjectTemplate filter) throws SQLException;
    int insertObjectTemplate(ObjectTemplate object) throws SQLException;
    void updateObjectTemplate(ObjectTemplate object) throws SQLException;
    void deleteObjectTemplate(ObjectTemplate object) throws SQLException;

    ArrayList<Attribute> selectAttribute(Attribute filter) throws SQLException;
    int insertAttribute(Attribute object) throws SQLException;
    void updateAttribute(Attribute object) throws SQLException;
    void deleteAttribute(Attribute object) throws SQLException;

    void createTablesToDb() throws SQLException;
}
