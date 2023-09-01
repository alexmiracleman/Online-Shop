package org.alex.dao.jdbc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
public class ConnectionFactory {
    private String url;
    private String username;
    private String password;

    public Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            throw new RuntimeException("Error connecting to database", e);
        }
    }
}
