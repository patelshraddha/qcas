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
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.questions.Question;
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;
import qcas.operations.user.User;
import sun.security.krb5.internal.rcache.AuthList;

/**
 *
 * @author Dell
 */
public class StudentHandler {
    
    public static LinkedHashMap getStudentActivity(DatabaseHandler database, int user_id) {
        
        LinkedHashMap result = new LinkedHashMap();
        
        try {
            ResultSet rs;
            
            String query = "SELECT COUNT(*), MONTH(exam_date) FROM exam WHERE YEAR(exam_date) = YEAR(curdate()) AND user_key = ? GROUP BY MONTH(exam_date)";     //past month
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,user_id);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {
                String month = null;
                
                if(rs.getInt(2)==1){
                    month = "January";
                }
                else
                if(rs.getInt(2)==2){
                    month = "February";
                }
                else
                    if(rs.getInt(2)==3){
                    month = "March";
                }
                else
                if(rs.getInt(2)==4){
                    month = "April";
                }
                else
                if(rs.getInt(2)==5){
                    month = "May";
                }
                else
                if(rs.getInt(2)==6){
                    month = "June";
                }
                else
                if(rs.getInt(2)==7){
                    month = "July";
                }
                else
                if(rs.getInt(2)==8){
                    month = "August";
                }
                else
                if(rs.getInt(2)==9){
                    month = "September";
                }
                else
                if(rs.getInt(2)==10){
                    month = "October";
                }
                else
                if(rs.getInt(2)==11){
                    month = "November";
                     
                }
                else
                if(rs.getInt(2)==12) 
                {
                    month = "December";
                    
                }
                    
                  
                result.put(month, rs.getInt(1));

            }
            
           

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return result;
    }

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

                questions.add(getQuestion(rs.getString("id"),rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("valid_1"), rs.getString("choice_2"), rs.getString("valid_2"), rs.getString("choice_3"), rs.getString("valid_3"), rs.getString("choice_4"), rs.getString("valid_4")));
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

                questions.add(getQuestion(rs.getString("id"),rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("valid_1"), rs.getString("choice_2"), rs.getString("valid_2"), rs.getString("choice_3"), rs.getString("valid_3"), rs.getString("choice_4"), rs.getString("valid_4")));
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

                questions.add(getQuestion(rs.getString("id"),rs.getString("question_type"), rs.getString("question_level"), rs.getString("question_description"), rs.getString("subject_code"), rs.getString("choice_1"), rs.getString("valid_1"), rs.getString("choice_2"), rs.getString("valid_2"), rs.getString("choice_3"), rs.getString("valid_3"), rs.getString("choice_4"), rs.getString("valid_4")));
            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return questions;
    }

    private static Question getQuestion(String id,String questionType, String questionLevel, String questionDescription, String subjectCode, String choice1, String valid1, String choice2, String valid2, String choice3, String valid3, String choice4, String valid4) {
        Question question = new Question();
        switch (questionType) {
            case "FIB":
                question = (new QuestionFIB()).getQuestion(id,questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            case "TF":
                question = (new QuestionTF()).getQuestion(id,questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            case "MA":
                question = (new QuestionMultipleAnswer()).getQuestion(id, questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            case "MC":
                question = (new QuestionMultipleChoice()).getQuestion(id,questionType, questionLevel, questionDescription, subjectCode, choice1, valid1, choice2, valid2, choice3, valid3, choice4, valid4);
                break;
            default:
                break;
        }

        return question;
    }
    
    public static int insertSelection(DatabaseHandler database, User user, ArrayList<Question> quizAnswers, String subjectCode, int noQuestions, String difficulty, int correctQuestions, int[] isCorrect){
        String insertSelectionQuery = "Insert into exam (user_key, subject_code, exam_date, difficulty_level, question_count, score, result, grade) values (?,?,?,?,?,?,?,?)";
        ResultSet rs;
        long timeNow = System.currentTimeMillis();
        java.sql.Timestamp timestamp = new java.sql.Timestamp(timeNow);
        int noChange=0;
        try {
            
            PreparedStatement ps = database.getConnection().prepareStatement(insertSelectionQuery);
            ps.setInt(1, Integer.parseInt(user.getUserKey()));
            ps.setInt(2, Integer.parseInt(subjectCode));
            ps.setTimestamp(3, timestamp);
            ps.setString(4, difficulty);
            ps.setInt(5,noQuestions);
            ps.setInt(6, correctQuestions);
            //Pass/Fail and Grade Logic here
            double percent = (correctQuestions/noQuestions)*100;
            if(percent>=60){
                ps.setInt(7, 1);
            }else{
                ps.setInt(7, 0);
            }
            String grade = null;
            if(percent<60&&percent>=0){
                grade="F";
            }else if(percent>=60&&percent<70){
                grade="C";
            }else if(percent>=70&&percent<80){
                grade="B";
            }else if(percent>=80&&percent<90){
                grade="B+";
            }else if(percent>=90&&percent<100){
                grade="A";
            }else if(percent==100){
                grade="A+";
            }
            ps.setString(8, grade);
            ps.executeUpdate();
            insertSelectionQuery = "SELECT exam_key FROM exam WHERE user_key = ? AND exam_date=?";
            ps = database.getConnection().prepareStatement(insertSelectionQuery);
            ps.setInt(1, Integer.parseInt(user.getUserKey()));
            ps.setTimestamp(2, timestamp);
            rs = ps.executeQuery();
            rs.next();
            int exam_key = rs.getInt(1);
            int i=0;
            for(Question quizQuestion: quizAnswers){        
                insertSelectionQuery = "INSERT INTO ExamLog(exam_key, question_key, select_1, select_2, select_3, select_4, correct) VALUES (?,?,?,?,?,?,?)";
                ps = database.getConnection().prepareStatement(insertSelectionQuery);
                ps.setInt(1, exam_key);
                ps.setInt(2, Integer.parseInt(quizQuestion.getId()));
                if((quizQuestion instanceof QuestionMultipleAnswer)){
                    int[] ans = ((QuestionMultipleAnswer)quizQuestion).getAnswer();
                    ps.setString(3, Integer.toString(ans[0]));
                    ps.setInt(4, ans[1]);
                    ps.setInt(5, ans[2]);
                    ps.setInt(6, ans[3]);
                }else if ((quizQuestion instanceof QuestionMultipleChoice)) {
                    int ans = ((QuestionMultipleChoice)quizQuestion).getAnswer();
                    ps.setString(3, "0");
                    ps.setInt(4, 0);
                    ps.setInt(5, 0);
                    ps.setInt(6, 0);
                    switch(ans){
                        case 1: ps.setString(3, "1");
                           break;
                        case 2:case 3:case 4: ps.setString(ans, "1");
                        break;
                    }
                }else if((quizQuestion instanceof QuestionTF)){
                    boolean temp = ((QuestionTF)quizQuestion).getAnswer();
                    if(temp){
                        ps.setString(3, "True");
                    }else{
                        ps.setString(3, "False");
                    }
                    ps.setInt(4, 0);
                    ps.setInt(5, 0);
                    ps.setInt(6, 0);
                }else if((quizQuestion instanceof QuestionFIB)){
                    String temp = ((QuestionFIB) quizQuestion).getAnswer();
                    ps.setString(3, temp);
                    ps.setInt(4, 0);
                    ps.setInt(5, 0);
                    ps.setInt(6, 0);
                }
                ps.setInt(7, isCorrect[i]);
                ps.executeUpdate();
                i++;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(StudentHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return noChange;
    }
}
