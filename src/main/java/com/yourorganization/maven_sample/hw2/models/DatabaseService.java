package com.yourorganization.maven_sample.hw2.models;

import java.sql.*;

public class DatabaseService {

    private static Connection connection;
    private static Statement statement;
    public static void connect()
    {
        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5437/test-db", "sa", "admin");

            statement = connection.createStatement();

        } catch (SQLException e) {
             e.printStackTrace();
        }
    }
    public static void close() {

    }
    public static ResultSet executeQuerySQL(String query)
    {
        try {
            if(connection == null || statement == null)
                connect();

            return statement.executeQuery(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static void executeSQL(String query)
    {
        try {
            if(connection == null || statement == null)
                connect();

            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
