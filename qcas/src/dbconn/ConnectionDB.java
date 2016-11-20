/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dbconn;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author Dell
 */
public class ConnectionDB {
    public Connection conn = null;
    
    public ConnectionDB() {

        System.out.println("-------- MySQL JDBC Connection Testing ------------");

        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Where is your MySQL JDBC Driver?");
            e.printStackTrace();

        }

        //System.out.println("MySQL JDBC Driver Registered!");
        

        try {
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://138.197.194.23:3306/qcas", "akshay", "akshayakshay");
            //.getConnection("jdbc:mysql://159.203.173.203:3306", "akshay", "akshayakshay");

        } catch (SQLException e) {
            System.out.println("Connection Failed! Check output console");
            e.printStackTrace();

        }

        if (conn != null) {
            System.out.println("You made it, take control your database now!");
        } else {
            System.out.println("Failed to make connection!");
        }
    }

}
