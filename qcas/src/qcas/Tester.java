/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.util.ArrayList;
import qcas.csvreader.CSVReader;
import qcas.questions.operations.Question;

import dbconn.*;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deepak
 */
public class Tester {

    public static void main(String[] args) {
        CSVReader reader = new CSVReader("test.csv", "OOP");
        if (reader.ParseCSV()) {
            ArrayList<Question> questions = reader.getQuestions();
            for (Question question : questions) {
                System.out.println(question);
            }
        } else {
        }
        try {

            System.out.println("Start");
            // Class.forName("com.mysql.jdbc.Driver").newInstance();
            //Connection con =  DriverManager.getConnection("jdbc:mysql://138.197.194.23:3306/qcas", "akshay", "akshayakshay");
            ConnectionDB con = new ConnectionDB();

            Statement st = con.conn.createStatement();
            ResultSet rs;
            //String q = "SELECT id,user_key,firstname,lastname,email,reg_date FROM Professor;";
            //Statement stmt = con.conn.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            System.out.println("Test");
            rs = st.executeQuery("SELECT id,user_key,firstname,lastname,email,reg_date FROM Professor");
            System.out.println("Test2");
            while (rs.next()) {
                System.out.printf("%d%d%s%s%s%s",rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getTimestamp(6));
            }
            System.out.println("Test3");
        } catch (SQLException ex) {
            //Logger.getLogger(Tester.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Unable to read data!!");
            System.out.println(ex);
        }

    }
}
