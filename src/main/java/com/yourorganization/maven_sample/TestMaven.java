package com.yourorganization.maven_sample;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.yourorganization.maven_sample.hw2.models.Dispatcher;
import com.yourorganization.maven_sample.hw2.models.Request;
import com.yourorganization.maven_sample.hw2.models.Vehicle;

import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import java.time.LocalDateTime;
import java.util.Random;


public class TestMaven {
    public static void main(String[] args) {

        //TASK 1.3
        // TASK 1.3 ///////////


//        String jdbcUrl = "jdbc:postgresql://localhost:5437/test-db";
//        String username = "sa";
//        String password = "admin";
//
//        try {
//            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//            Random random = new Random();
//
//            while (true) {
//                String message;
//                String type;
//                boolean processed = false;
//
//                if (random.nextBoolean()) {
//                    message = "Новое сообщение от " + LocalDateTime.now();
//                    type = "INFO";
//                } else {
//                    message = "Произошла ошибка в " + LocalDateTime.now();
//                    type = "WARN";
//                }
//
//                String insertQuery = "INSERT INTO notice (message, type, processed) VALUES (?, ?, ?)";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
//                    preparedStatement.setString(1, message);
//                    preparedStatement.setString(2, type);
//                    preparedStatement.setBoolean(3, processed);
//                    preparedStatement.executeUpdate();
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                Thread.sleep(1000);
//            }
//        } catch (SQLException | InterruptedException e) {
//            e.printStackTrace();
//        }



        // TASK 1.4
//        String jdbcUrl = "jdbc:postgresql://localhost:5437/test-db";
//        String username = "sa";
//        String password = "admin";
//
//        try {
//            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//
//            while (true) {
//                String selectQuery = "SELECT id, message FROM notice WHERE type = 'INFO' AND processed = false";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
//                    ResultSet resultSet = preparedStatement.executeQuery();
//
//                    while (resultSet.next()) {
//                        int id = resultSet.getInt("id");
//                        String message = resultSet.getString("message");
//
//                        System.out.println("Received INFO message: " + message);
//
//                        String deleteQuery = "DELETE FROM notice WHERE id = ?";
//                        try (PreparedStatement deleteStatement = connection.prepareStatement(deleteQuery)) {
//                            deleteStatement.setInt(1, id);
//                            deleteStatement.executeUpdate();
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                Thread.sleep(1000);
//            }
//        } catch (SQLException | InterruptedException e) {
//            e.printStackTrace();
//        }





        // TASK 1.5


//        String jdbcUrl = "jdbc:postgresql://localhost:5437/test-db";
//        String username = "sa";
//        String password = "admin";
//
//        try {
//            Connection connection = DriverManager.getConnection(jdbcUrl, username, password);
//
//            while (true) {
//                String selectQuery = "SELECT id, message FROM notice WHERE type = 'WARN' AND processed = false";
//
//                try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
//                    ResultSet resultSet = preparedStatement.executeQuery();
//
//                    while (resultSet.next()) {
//                        int id = resultSet.getInt("id");
//                        String message = resultSet.getString("message");
//
//                        System.out.println("Received WARN message: " + message);
//
//                        String updateQuery = "UPDATE notice SET processed = false WHERE id = ?";
//                        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
//                            updateStatement.setInt(1, id);
//                            updateStatement.executeUpdate();
//                        }
//                    }
//                } catch (SQLException e) {
//                    e.printStackTrace();
//                }
//
//                Thread.sleep(1000);
//            }
//        } catch (SQLException | InterruptedException e) {
//            e.printStackTrace();
//        }


        Dispatcher dispatcher = new Dispatcher();
        dispatcher.start();
    }



}
