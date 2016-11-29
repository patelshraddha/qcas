/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 *
 * @author Dell
 */
public class SubjectHandler {

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
