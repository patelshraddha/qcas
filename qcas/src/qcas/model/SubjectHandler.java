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
public class SubjectHandler {
    public static ArrayList<String> getAllSubject(DatabaseHandler database, User user) {
             ArrayList<String> list = new ArrayList<String>();
        try {
            ResultSet rs;
            String insertquestionsquery = "SELECT *"
                    + " FROM 'subject'";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                list.add(rs.getString(2));
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return list;      
    }
}
