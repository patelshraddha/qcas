/*
 */
package qcas.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.scene.control.MenuItem;
import qcas.operations.user.User;

/**
 *
 * @author Deepak
 */
public class UserRegisterTableHandler {

    public static String saveUser(DatabaseHandler database, String userName, String password,String firstName, String secondName,String emailID,ArrayList<String> subjectMenuList) {
        //Please see the first variable.Dont know what ot do with it
        String userID = null;
        boolean verified = false;
        try {
            
            
            ResultSet rs;
            
            String insertuserquery = "INSERT INTO `USERLOGIN` (`login_id`, `password`,`status_locked`,`type`)"
                    +" VALUES(?,?,'0','s')";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertuserquery);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);
             preparedStatement.execute();

                        ResultSet rs1;
            String insertuserquery1 = "SELECT  `user_key` FROM  `USERLOGIN` "
                    +"WHERE login_id =?";
            PreparedStatement preparedStatement1 = database.getConnection().prepareStatement(insertuserquery1);
            preparedStatement1.setString(1, userName);
//            preparedStatement.setString(2, password);
            rs1 = preparedStatement1.executeQuery();
            rs1.next();
            userID=rs1.getString(1);
            
                        ResultSet rs2;
            String insertuserquery2 = "INSERT INTO `Student`(`user_key`, `firstname`, `lastname`, `email`)"
                    +"VALUES(?,?,?,?)";
            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement(insertuserquery2);
            preparedStatement2.setString(1, userID);
            preparedStatement2.setString(2, firstName);
            preparedStatement2.setString(3, secondName);
            preparedStatement2.setString(4, emailID);
            preparedStatement2.execute();
            
            
            
            
            for (String subject:subjectMenuList){
            
            String ID= UserRegisterTableHandler.getSubjectWithName(database, subject);
            ResultSet rs10;
            String insertuserquery10 = "INSERT INTO `user_subject_relation`(`user_key`, `subject_code`) "
                    +"VALUES (?,?)";
            PreparedStatement preparedStatement10 = database.getConnection().prepareStatement(insertuserquery10);
             preparedStatement10.setString(1, userID);
            preparedStatement10.setString(2, ID);
            preparedStatement10.execute();
            
           
            }
            
            
            
            
            
            
            
            
            
            
            
            
            
        }
        
        catch (SQLException ex) {
            Logger.getLogger(UserRegisterTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
//User user=new User(userID, userName, firstName  , secondName, emailID," ", "s");

        return userName;
    }

    
    public static boolean isUsernamePresent(DatabaseHandler database, String username) {
        boolean verified = false;
        try {

            ResultSet rs;
            String insertquestionsquery = "SELECT type "
                    + "FROM USERLOGIN "
                    + "WHERE login_id =? ";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, username);
            rs = preparedStatement.executeQuery();

            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst();
            }

            if (rowcount == 1) {
                verified = true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verified;
    }
    
    /**
     * gets user details
     *
     * @param database database from which the details have to be fetched
     * @param username
     * @return User details
     */
    public static User getUser(DatabaseHandler database, String username) {

        try {

            ResultSet rs;
            //get the user details from the user table
            String insertquestionsquery = "SELECT user_key,type "
                    + "FROM USERLOGIN "
                    + "WHERE login_id=? ";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, username);
            // preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            String type;
            int userKey;
            rs.next();
            userKey = rs.getInt(1);
            type = rs.getString(2);

            if (type.equals(qcas.Constants.PROFESSORTYPE)){
                insertquestionsquery = "SELECT * FROM Professor WHERE user_key=?";
            } else if (type.equals(qcas.Constants.STUDENTTYPE)){
                insertquestionsquery = "SELECT * FROM Student WHERE user_key=?";
            }
            preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setInt(1, userKey);
            rs = preparedStatement.executeQuery();

            rs.next();
            
            return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6),type);

        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }
    
    
    public static String getSubjectWithName(DatabaseHandler database, String subjectName) {
String subjectID = null;
        try {

            ResultSet rs;
            //get the user details from the user table
            String insertquestionsquery = "SELECT `subject_code` FROM `subject`  "
                    + "WHERE `subject_name`=? ";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, subjectName);
            // preparedStatement.execute();
            rs = preparedStatement.executeQuery();
             
            //int userKey;
            rs.next();
            subjectID = rs.getString(1);
        } catch (SQLException ex) {
            Logger.getLogger(UserRegisterTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return subjectID;

    }
    
    
}
