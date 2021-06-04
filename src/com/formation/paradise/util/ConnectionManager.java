package com.formation.paradise.util;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {

    private static Connection connection;
    private static final String URL = "jdbc:mysql://localhost:8889/paradise";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private ConnectionManager(){

    }


    public static Connection getConnection(){
        if (connection == null){
            try {
                loadDriver();
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                connection.setAutoCommit(false);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void loadDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
            System.err.println("Driver Mysql introuvable");
        }
    }

    public static void closeConnection(){
        try {
            if (getConnection() != null){
                getConnection().close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
