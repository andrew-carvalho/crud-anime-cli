package com.andrew.domain;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static Connection getConnection() {
        String databaseUrl = "jdbc:mysql://localhost:3306/anime";
        String username = "root";
        String password = "root";

        Connection connection = null;

        try {
            connection = DriverManager.getConnection(databaseUrl, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return connection;
    }

}
