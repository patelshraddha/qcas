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
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.questions.Question;
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;

/**
 *
 * @author Dell
 */
public class StudentHandler {

    public static HashMap<String, Integer> getCountQuestions(DatabaseHandler database, String subjectCode) {
        HashMap<String, Integer> hmap = new HashMap<String, Integer>();
        try {
            ResultSet rs;

            //get the user details from the user table
            String insertquestionsquery = "SELECT question_level,COUNT(*) FROM `questionBank`"
                    + " where subject_code= ?"
                    + "GROUP BY question_level";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, subjectCode);

            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                hmap.put(rs.getString(1), Integer.parseInt(rs.getString(2)));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hmap;

    }

    public static ArrayList<Question> getQuestions(DatabaseHandler database, String subjectCode, HashMap<String, Integer> hm) {
        ArrayList<Question> questions = new ArrayList<Question>();
        System.out.println(subjectCode);
        try {
            ResultSet rs;

            String insertquestionsquery = "SELECT * FROM `questionBank`"
                    + " where question_level='H' AND subject_code= ? "
                    + "ORDER BY RAND() LIMIT ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
            preparedStatement.setString(1, subjectCode);
            preparedStatement.setInt(2, hm.get("H"));
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                questions.add(getQuestion(rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("valid_1"), rs.getString("choice_2"), rs.getString("valid_2"), rs.getString("choice_3"), rs.getString("valid_3"), rs.getString("choice_4"), rs.getString("valid_4")));
            }

            String insertquestionsquery2 = "SELECT * FROM `questionBank`"
                    + " where question_level='M' AND subject_code= ? "
                    + "ORDER BY RAND() LIMIT ?";
            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement(insertquestionsquery2);
            preparedStatement2.setString(1, subjectCode);
            preparedStatement2.setInt(2, hm.get("M"));
            //preparedStatement.execute();
            rs = preparedStatement2.executeQuery();
            while (rs.next()) {

                questions.add(getQuestion(rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("valid_1"), rs.getString("choice_2"), rs.getString("valid_2"), rs.getString("choice_3"), rs.getString("valid_3"), rs.getString("choice_4"), rs.getString("valid_4")));
            }

            String insertquestionsquery3 = "SELECT * FROM `questionBank`"
                    + " where question_level='E' AND subject_code= ? "
                    + "ORDER BY RAND() LIMIT ?";
            PreparedStatement preparedStatement3 = database.getConnection().prepareStatement(insertquestionsquery3);
            preparedStatement3.setString(1, subjectCode);
            preparedStatement3.setInt(2, hm.get("E"));
            //preparedStatement.execute();
            rs = preparedStatement3.executeQuery();
            while (rs.next()) {

                questions.add(getQuestion(rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("valid_1"), rs.getString("choice_2"), rs.getString("valid_2"), rs.getString("choice_3"), rs.getString("valid_3"), rs.getString("choice_4"), rs.getString("valid_4")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    private static Question getQuestion(String questionType, String questionLevel, String questionDescription, String subjectCode, String choice1, String valid1, String choice2, String valid2, String choice3, String valid3, String choice4, String valid4) {
        Question question = new Question();
        switch (questionType) {
            case "FIB":
                question = (new QuestionFIB()).getQuestion(questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            case "TF":
                question = (new QuestionTF()).getQuestion(questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            case "MA":
                question = (new QuestionMultipleAnswer()).getQuestion(questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            case "MC":
                question = (new QuestionMultipleChoice()).getQuestion(questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            default:
                break;
        }

        return question;
    }

}
