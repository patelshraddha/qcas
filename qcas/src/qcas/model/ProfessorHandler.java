/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.user.User;

/**
 *
 * @author Dell
 */
public class ProfessorHandler {

    public static ArrayList<String> getSubject(DatabaseHandler database, User user) {
             ArrayList<String> list = new ArrayList<String>();
        try {
            ResultSet rs;
           
            //get the user details from the user table
            String insertquestionsquery = "SELECT subject.subject_name AS subject FROM user_subject_relation INNER JOIN qcas.subject ON user_subject_relation.subject_code = subject.subject_code"
                    + " WHERE user_subject_relation.user_key = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
           preparedStatement.setString(1,user.getId());
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                list.add(rs.getString(1));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return list;      

    }
    public static ArrayList<String> getAllSubject(DatabaseHandler database) {
             ArrayList<String> list = new ArrayList<String>();
        try {
            ResultSet rs;
           
            //get the user details from the user table
            String insertquestionsquery = "SELECT subject_name "
                    + " FROM `subject`";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
           //preparedStatement.setString(1,user.getId());
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                list.add(rs.getString(1));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return list;      

    }
}
