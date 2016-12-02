package qcas.model;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.subject.Subject;
import qcas.operations.user.User;

/**
 *Subject handler in database
 * @author Akshay Thorat
 * @author Aniket Jain
 */
public class SubjectHandler {

    /**
     *Subject handler in database
     * @param database
     * @param user
     * @return
     */
    public static ArrayList<Subject> getSubjectUser(DatabaseHandler database, User user) {
        ArrayList<Subject> list = new ArrayList<Subject>();
        try {
            ResultSet rs;

            //get the user details from the user table
            String insertquestionsquery = "SELECT subject.subject_code,subject.subject_name AS subject FROM user_subject_relation INNER JOIN qcas.subject ON user_subject_relation.subject_code = subject.subject_code"
                    + " WHERE user_subject_relation.user_key = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, user.getUserKey());
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                list.add(new Subject(rs.getString(1), rs.getString(2)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProfessorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;

    }

    /**
     *gets all the subjects
     * @param database
     * @return
     */
    public static ArrayList<Subject> getAllSubjects(DatabaseHandler database) {
        ArrayList<Subject> list = new ArrayList<Subject>();
        try {
            ResultSet rs;

            String getsubjectsquery = "SELECT subject_code,subject_name "
                    + " FROM `subject`";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(getsubjectsquery);
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                list.add(new Subject(rs.getString(1), rs.getString(2)));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }
}
