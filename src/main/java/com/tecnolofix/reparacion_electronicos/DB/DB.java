package com.tecnolofix.reparacion_electronicos.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB implements AutoCloseable {
    private static String url;
    private static String username;
    private static String password;

    private Connection connection;


    public static void setUrl(String url) {
        DB.url = url;
    }

    public static void setUsername(String username) {
        DB.username = username;
    }

    public static void setPassword(String password) {
        DB.password = password;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }

    public static String getUrl() {
        return url;
    }

    public Connection getConnection() throws SQLException {
        if(connection == null||connection.isClosed()) {
            connection = DriverManager.getConnection(url,username,password);
        }
        return connection;
    }

    @Override
    public void close() throws Exception {
        if(connection != null && !connection.isClosed()) {
            connection.close();
        }
    }
}