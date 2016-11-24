/*
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
 * @author Deepak
 */
public class UserLoginTableHandler {

    public static boolean verifyLogin(DatabaseHandler database, String username, String password) {
        boolean verified = false;
        try {

            ResultSet rs;
            String insertquestionsquery = "SELECT `type` "
                    + "FROM USERLOGIN "
                    + "WHERE `login_id` =? "
                    + "AND `PASSWORD` =? "
                    + "LIMIT 0 , 30";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

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
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verified;
    }

    /**
     * gets user details
     * @param database database from which the details have to be fetched
     * @param loginid id of the user 
     * @return User details
     */
    public static User getUser(DatabaseHandler database, String username) {
        boolean verified = false;
        Statement st;

        try {

            ResultSet rs;
            //get the user details from the user table
            String insertquestionsquery = "SELECT `type` "
                    + "FROM USERLOGIN "
                    + "WHERE `login_id` =? "
                    + "LIMIT 0 , 30";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, username);
            
            // preparedStatement.execute();
            rs = preparedStatement.executeQuery();

            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            if (rowcount == 1) {
                verified = true;
            }
           return new User("1",username,"Rahul","Baijal","badass.com","500A.D.","p");

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        
    }

}
