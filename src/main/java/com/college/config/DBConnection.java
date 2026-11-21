
package com.college.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

import com.college.exception.DatabaseException;

public class DBConnection {

    private static final Properties properties = new Properties();

    static {
        try (InputStream input =
             DBConnection.class.getClassLoader()
             .getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new IllegalStateException(
                    "db.properties file not found"
                );
            }

            properties.load(input);

        } catch (Exception e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    public static Connection getConnection()
            throws DatabaseException {

        try {
            return DriverManager.getConnection(
                properties.getProperty("db.url"),
                properties.getProperty("db.username"),
                properties.getProperty("db.password")
            );

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException("Unable to connect to database", e);
        }
    }
}
