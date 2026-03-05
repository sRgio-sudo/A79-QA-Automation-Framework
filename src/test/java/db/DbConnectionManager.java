package db;

import utils.ConfigReader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnectionManager {

    private static Connection connection;

    private DbConnectionManager() {}

    public static Connection getConnection() {

        if (connection == null) {
            try {
                Class.forName("org.mariadb.jdbc.Driver");
                connection = DriverManager.getConnection(
                        ConfigReader.getProperty("db.url"),
                        ConfigReader.getProperty("db.user"),
                        ConfigReader.getProperty("db.password")
                );
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("MariaDB Driver not found!", e);
            } catch (SQLException e) {
                throw new RuntimeException("Database connection failed", e);
            }
        }
        return connection;
    }
}