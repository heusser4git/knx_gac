package ch.ibw.knxgac.Repository.Database;

import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.ArrayList;

public interface Database {
    boolean createConnection(String databaseTyp, String dbServer, int dbServerPort, String database, String user, String password) throws SQLException;
    boolean isConnected() throws SQLException;
    void closeConnection() throws SQLException;

    <T> boolean createTable(T object) throws SQLException;
    <T> ArrayList<T> select(T filter) throws SQLException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, InstantiationException;
    <T> boolean insert(T object) throws SQLException;
    <T> boolean update(T object) throws SQLException;

    <T> boolean delete(T object) throws SQLException;

}
