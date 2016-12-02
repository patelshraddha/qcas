
package qcas.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.user.User;

/**
 * User login table in database handler 
 * @author Shraddha Patel
 */
public class UserLoginTableHandler {

    /**
     * Verifies the login
     * @param database
     * @param username
     * @param password
     * @return
     */
    public static boolean verifyLogin(DatabaseHandler database, String username, String password) {
        boolean verified = false;
        try {

            ResultSet rs;
            String insertquestionsquery = "SELECT type "
                    + "FROM USERLOGIN "
                    + "WHERE login_id =? "
                    + "AND password =? ";
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
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
