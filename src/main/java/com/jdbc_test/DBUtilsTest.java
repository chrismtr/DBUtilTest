package com.jdbc_test;

import java.sql.*;
import java.util.Arrays;

public class DBUtilsTest {

    public static void main(String[] args) throws Exception{

        Connection connection = getConnection();
        ResultSet resultSet = getResultSet(connection);

        Object[] objectStorage = new Object[0];
        while (resultSet.next()) {

            User user = createUser(resultSet);
            objectStorage = addObjectToLocalStorage(objectStorage, user);
        }
        System.out.println(Arrays.toString(objectStorage));
        connection.close();
    }

    private static Object[] addObjectToLocalStorage(Object[] objectStorage, User user) {
        objectStorage = Arrays.copyOf(objectStorage, objectStorage.length + 1);
        objectStorage[objectStorage.length - 1] = user;
        return objectStorage;
    }

    private static User createUser(ResultSet resultSet) throws SQLException {
        String id = resultSet.getString("id");
        String name = resultSet.getString("name");
        return new User(id, name);
    }

    private static ResultSet getResultSet(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement("SELECT id, name FROM users");
        return preparedStatement.executeQuery();
    }

    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");

        String username = "root";
        String password = "12345";
        String url = "jdbc:h2:file:C:/Users/deigr/Repository/Java/orm_annotation_db_test";
        return DriverManager.getConnection(url, username, password);
    }
}
