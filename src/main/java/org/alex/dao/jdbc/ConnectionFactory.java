package org.alex.dao.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    private String url;
    private String username;
    private String password;

    public ConnectionFactory(Properties properties) {
        this.url = properties.getProperty("url");
        this.username = properties.getProperty("login");
        this.password = properties.getProperty("password");


    }
    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
