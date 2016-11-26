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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.questions.Question;
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;
import qcas.operations.user.User;
/**
 *
 * @author Dell
 */
public class StudentHandler {
    public static HashMap<String,Integer> getSubject(DatabaseHandler database, String subjectCode) {
             HashMap<String,Integer> hmap = new HashMap<String,Integer>();
        try {
            ResultSet rs;
           
            //get the user details from the user table
            String insertquestionsquery = "SELECT question_level,COUNT(*) FROM `questionBank`"
                    + " where subject_code= ?"
                    +"GROUP BY question_level";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
           preparedStatement.setString(1,subjectCode);
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
    
        public static ArrayList<Question> getQuestions(DatabaseHandler database, HashMap<String,Integer> hm) {
            ArrayList<Question> questions=new ArrayList<Question>();
            
        try {
            ResultSet rs;
           
            String insertquestionsquery = "SELECT * FROM `questionBank`"
                    + " where question_level='H'"
                    +"ORDER BY RAND() LIMIT ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
           preparedStatement.setInt(1,hm.get("H"));
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                   questions.add(new Question(rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("choice_2"), rs.getString("choice_3"), rs.getString("choice_4")));
            }
            
         String insertquestionsquery2 = "SELECT * FROM `questionBank`"
                    + " where question_level='M'"
                    +"ORDER BY RAND() LIMIT ?";
            PreparedStatement preparedStatement2 = database.getConnection().prepareStatement(insertquestionsquery2);
           preparedStatement2.setInt(1,hm.get("M"));
            //preparedStatement.execute();
            rs = preparedStatement2.executeQuery();
            while (rs.next()) {
                   questions.add(new Question(rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("choice_2"), rs.getString("choice_3"), rs.getString("choice_4")));
            }
        
        String insertquestionsquery3 = "SELECT * FROM `questionBank`"
                    + " where question_level='E'"
                    +"ORDER BY RAND() LIMIT 3";
            PreparedStatement preparedStatement3 = database.getConnection().prepareStatement(insertquestionsquery3);
           preparedStatement.setInt(1,hm.get("L"));
            //preparedStatement.execute();
            rs = preparedStatement3.executeQuery();
            while (rs.next()) {
                   questions.add(new Question(rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("choice_2"), rs.getString("choice_3"), rs.getString("choice_4")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return questions;      
    }
}
