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
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.user.User;
/**
 *
 * @author Dell
 */
public class ProfessorHandler {
    public static void getSubject(DatabaseHandler database, User user) {
        try {
            ResultSet rs;
            
            //get the user details from the user table
            String insertquestionsquery = "SELECT USERLOGIN.login_id as user, subject.subject_name as subject "
                    + "FROM user_subject_relation "
                    + "INNER JOIN qcas.USERLOGIN"
                    + "ON user_subject_relation.user_key = USERLOGIN.user_key"
                    + "INNER JOIN qcas.subject "
                    + "ON user_subject_relation.subject_code = subject.subject_code"
                    + "WHERE USERLOGIN.login_id='athorat' ";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, user.getId());
            // preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next())
      {
 
        String subject = rs.getString("subject");
          System.out.println(subject);

       
      }
            
            
            //return null;

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        //return null;

    }
}
