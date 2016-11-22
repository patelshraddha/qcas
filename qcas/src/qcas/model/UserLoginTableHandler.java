/*
 */
package qcas.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Deepak
 */
public class UserLoginTableHandler {

    public static boolean verifyLogin(DatabaseHandler database, String username, String password) {
        boolean verified = false;
        Statement st;

        try {
            st = database.getConnection().createStatement();
            ResultSet rs;
            rs = st.executeQuery("SELECT `type` \n"
                    + "FROM USERLOGIN\n"
                    + "WHERE `login_id` =  '" + username + "'\n"
                    + "AND `PASSWORD` =  '" + password + "'\n"
                    + "LIMIT 0 , 30");
            int rowcount = 0;
            if (rs.last()) {
                rowcount = rs.getRow();
                rs.beforeFirst(); // not rs.first() because the rs.next() below will move on, missing the first element
            }

            if(rowcount==1)
                verified = true;

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return verified;
    }

}
