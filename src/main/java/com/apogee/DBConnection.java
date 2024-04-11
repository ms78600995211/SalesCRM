/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.apogee;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Vikranth
 */
public class DBConnection {

    static ResourceBundle resourceBundle = ResourceBundle.getBundle("db");

    static String driverClass = resourceBundle.getString("DATABASE_DRIVERCLASS");
    static String connectionString = resourceBundle.getString("DATABASE_CONNECTIONSTRING");
    static String db_username = resourceBundle.getString("DATABASE_USERNAME");
    static String db_password = resourceBundle.getString("DATABASE_PASSWORD");

    public static synchronized Connection getConnection(ServletContext ctx, HttpSession session) throws SQLException {
        Connection conn = null;
        //        try {
//            Class.forName(ctx.getInitParameter("driverClass"));
//            conn = DriverManager.getConnection(ctx.getInitParameter("connectionString"), ctx.getInitParameter("db_username"), ctx.getInitParameter("db_password"));
//        } catch (Exception e) {
//            System.out.println("DBConncetion getConnection() Error: " + e);
//        }
        try {
            Class.forName(driverClass);
            conn = DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }

    public static Connection getConnection(ServletContext ctx) throws SQLException {
        Connection conn = null;
        //        try {
//            Class.forName(ctx.getInitParameter("driverClass"));
//            conn = (Connection) DriverManager.getConnection(ctx.getInitParameter("connectionString"), (String) ctx.getInitParameter("db_username"), (String) ctx.getInitParameter("db_password"));
//        } catch (Exception e) {
//            System.out.println("DBConncetion getConnection() Error: " + e);
//        }
        try {
            Class.forName(driverClass);
            conn = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
        } catch (Exception e) {
            System.out.println("DBConncetion getConnection() Error: " + e);
        }
        return conn;
    }

//    public static Connection getConnection1() throws SQLException {
//      Connection conn = null;
//        try {
//            Class.forName(driverClass);
//            conn = (Connection) DriverManager.getConnection(connectionString, db_username, db_password);
//        } catch (Exception e) {
//            System.out.println("DBConncetion getConnection() Error: " + e);
//        }
//        return conn;
//    }
    public static synchronized Connection getConnectionForUtf(ServletContext ctx) throws SQLException {
        Connection conn = null;
        try {
            Class.forName(ctx.getInitParameter("driverClass"));
            conn = (Connection) DriverManager.getConnection((String) ctx.getInitParameter("connectionString") + "?useUnicode=true&characterEncoding=UTF-8&character_set_results=utf8", (String) ctx.getInitParameter("db_username"), (String) ctx.getInitParameter("db_password"));
        } catch (Exception e) {
            System.out.println(" getConnectionForUtf() Error: " + e);
        }
        return conn;
    }

    public static void closeConncetion(Connection con) {

        try {
            con.close();
        } catch (Exception e) {
            System.out.println("DBConncetion closeConnection() Error: " + e);
        }

    }

}
