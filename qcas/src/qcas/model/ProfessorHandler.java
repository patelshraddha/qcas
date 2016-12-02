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
import java.util.List;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import qcas.operations.exam.Exam;

import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;
import qcas.operations.subject.Subject;
import qcas.operations.user.User;

/**
 * Database handler for professor database
 * @author Akshay Thorat
 * @author Aniket Jain
 */
public class ProfessorHandler {

    /**
     * gets all subjects for users
     * @param database
     * @param user
     * @return
     */
    public static ArrayList<String> getSubject(DatabaseHandler database, User user) {
             ArrayList<String> list = new ArrayList<String>();
        try {
            ResultSet rs;
           
            //get the user details from the user table
            String insertquestionsquery = "SELECT subject.subject_name AS subject FROM user_subject_relation INNER JOIN qcas.subject ON user_subject_relation.subject_code = subject.subject_code"
                    + " WHERE user_subject_relation.user_key = ?";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
           preparedStatement.setString(1,user.getId());
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                list.add(rs.getString(1));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return list;      

    }
    
    /**
     * gets all subjects
     * @param database
     * @return
     */
    public static ArrayList<String> getAllSubject(DatabaseHandler database) {
             ArrayList<String> list = new ArrayList<String>();
        try {
            ResultSet rs;
           
            //get the user details from the user table
            String insertquestionsquery = "SELECT subject_name "
                    + " FROM `subject`";
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
           //preparedStatement.setString(1,user.getId());
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                list.add(rs.getString(1));

            }

        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return list;      
    }

    /**
     * inserts questions in the database
     * @param database
     * @param questions
     * @return
     */
    public static int insertQuestions(DatabaseHandler database, ArrayList<qcas.operations.questions.Question> questions) {

        try {
            String typeTemp, sql = null;
            PreparedStatement ps = null;
            ResultSet rs;
            int j, k, n = 0;
            for (qcas.operations.questions.Question question : questions) {
                typeTemp = question.getType();

                switch (typeTemp) {
                    case "MA":
                        sql = "Insert into questionBank (question_type, question_level, question_description, subject_code, choice_1, valid_1, choice_2, valid_2,"
                                + "choice_3,valid_3, choice_4, valid_4) values ("
                                + "?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps = database.getConnection().prepareStatement(sql);
                        
                        ps.setString(1, question.getType());
                        ps.setString(2, question.getLevel());
                        ps.setString(3, question.getDescription());
                        ps.setString(4, question.getSubjectCode());
                        j = 0;
                        for (String ch : question.getChoices()) {
                            ps.setString(5 + j, ch);
                            j += 2;
                        }
                        k = 0;
                        for (int i : ((QuestionMultipleAnswer) question).getAnswer()) {
                            ps.setString(k + 6, Integer.toString(i));
                            k += 2;
                        }
                        break;
                    case "MC":
                        sql = "Insert into questionBank (question_type, question_level, question_description, subject_code, choice_1, valid_1, choice_2, valid_2,"
                                + "choice_3,valid_3, choice_4, valid_4) values ("
                                + "?,?,?,?,?,?,?,?,?,?,?,?)";
                        ps = database.getConnection().prepareStatement(sql);
                        
                        ps.setString(1, question.getType());
                        ps.setString(2, question.getLevel());
                        ps.setString(3, question.getDescription());
                        ps.setString(4, question.getSubjectCode());
                        j = 0;
                        for (String ch : question.getChoices()) {
                            ps.setString(5 + j, ch);
                            j += 2;
                        }
                        k = ((QuestionMultipleChoice) question).getAnswer();
                        for (int i = 0; i < 7; i += 2) {
                            if ((i / 2) == k) {
                                ps.setString(i + 6, "1");
                            } else {
                                ps.setString(i + 6, "0");
                            }
                        }
                        break;
                    case "FIB":
                        sql = "Insert into questionBank (question_type, question_level, question_description, subject_code, choice_1) values ("
                                + "?,?,?,?,?)";
                        ps = database.getConnection().prepareStatement(sql);
                        //ps.setInt(1, Integer.parseInt(question.getId()));
                        ps.setString(1, question.getType());
                        ps.setString(2, question.getLevel());
                        ps.setString(3, question.getDescription());
                        ps.setString(4, question.getSubjectCode());
                        String temp;
                        temp = ((QuestionFIB) question).getAnswer();
                        ps.setString(5, temp);
                        break;
                    case "TF":
                        sql = "Insert into questionBank (question_type, question_level, question_description, subject_code, choice_1) values ("
                                + "?,?,?,?,?)";
                        ps = database.getConnection().prepareStatement(sql);
                        ps.setString(1, question.getType());
                        ps.setString(2, question.getLevel());
                        ps.setString(3, question.getDescription());
                        ps.setString(4, question.getSubjectCode());
                        boolean x;
                        x = ((QuestionTF) question).getAnswer();
                        if (x == true) {
                            ps.setString(5, "True");
                        } else {
                            ps.setString(5, "False");
                        }
                        break;
                }
                //System.out.println(sql);
                ps.executeUpdate();
                n++;

            }
            return n;
        } catch (SQLException ex) {

            System.out.println(ex);
        }

        return 0;
    }
    
    /**
     * gets test taken for a subject
     * @param database
     * @param subjectCode
     * @return
     */
    public static ArrayList<Integer> getTestsTaken(DatabaseHandler database, int subjectCode) {
        ArrayList<Integer> testCount = new ArrayList<Integer>();
        
        try {
            ResultSet rs;
           
            //get tests taken in the past month
            String query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 MONTH) and subject_code = ?";     //past month
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                testCount.add(rs.getInt(1));

            }
            
            //get tests taken in the past month
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 4 MONTH) and subject_code = ?";     //past quarter
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                testCount.add(rs.getInt(1));

            }
            
            //get tests taken in the past month
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 YEAR) and subject_code = ?";     //past Year
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                testCount.add(rs.getInt(1));

            }
            
            
            
        }
        catch (SQLException ex) {

            System.out.println(ex);
        }

        return testCount;
        
    }
    
    /**
     * gets arraylist of average score of a subject
     * @param database
     * @param subjectCode
     * @return
     */
    public static ArrayList<Double> getAverageScores(DatabaseHandler database, int subjectCode){
        ArrayList<Double> averageScores = new ArrayList<Double>();
        
        try {
            ResultSet rs;
           
            //get avg scores in the past month
            String query = "SELECT AVG(score) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 MONTH) and subject_code = ?";     //past month
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
            
            //get avg scores in the past quarter
            query = "SELECT AVG(score) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 4 MONTH) and subject_code = ?";     //past quarter
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
            
            //get average scores in the past year
            query = "SELECT AVG(score) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 YEAR) and subject_code = ?";     //past Year
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
             
        }
        catch (SQLException ex) {

            System.out.println(ex);
        }

        return averageScores;
    }
    
    /**
     * gets scores level of a subject 
     * @param database
     * @param subjectCode
     * @return
     */
    public static ArrayList<Double> getScoresLevel(DatabaseHandler database, int subjectCode){
        ArrayList<Double> averageScores = new ArrayList<Double>();
        
        try {
            ResultSet rs;
            
            //get avg scores E
            String query = "SELECT AVG(score) FROM exam where difficulty_level = 'Easy' and subject_code = ?";     
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
            
            //get avg scores M
            query = "SELECT AVG(score) FROM exam where difficulty_level = 'Medium' and subject_code = ?";     
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
            
            //get average scores H
            query = "SELECT AVG(score) FROM exam where difficulty_level = 'Hard' and subject_code = ?";     //past Year
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
            
            //get average scores Mixed
            query = "SELECT AVG(score) FROM exam where difficulty_level = 'Mixed' and subject_code = ?";     //past Year
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                averageScores.add(rs.getDouble(1));

            }
             
        }
        catch (SQLException ex) {

            System.out.println(ex);
        }

        return averageScores;
    }

    public static ArrayList<Integer> getResultOverTime(DatabaseHandler database, int subjectCode){
        ArrayList<Integer> results = new ArrayList<Integer>();
        
        try {
            ResultSet rs;
           
            //number of students passed last month
            String query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 MONTH) and subject_code = ? and result = 1";     //passing past month
            PreparedStatement preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                results.add(rs.getInt(1));

            }
            
            //number of students failed last month
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 MONTH) and subject_code = ? and result = 0";     
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                results.add(rs.getInt(1));

            }
            
            //number of students passed last quarter
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 4 MONTH) and subject_code = ? and result = 1";    
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                results.add(rs.getInt(1));

            }
            
            
            //number of students failed last quarter
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 4 MONTH) and subject_code = ? and result = 0";     
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                results.add(rs.getInt(1));

            }
            
            
            //number of students passed last year
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 YEAR) and subject_code = ? and result = 1";     //passing past month
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                results.add(rs.getInt(1));

            }
            
            //number of students failed last year
            query = "SELECT count(*) FROM exam where exam_date > DATE_SUB(NOW(), INTERVAL 1 YEAR) and subject_code = ? and result = 0";     
            preparedStatement = database.getConnection().prepareStatement(query);
            preparedStatement.setInt(1,subjectCode);
            //preparedStatement.execute();
            rs = preparedStatement.executeQuery();
            while (rs.next()) {

                results.add(rs.getInt(1));

            }
            
            
        }
        catch (SQLException ex) {

            System.out.println(ex);
        }

        return results;
    }
    
    public static HashMap<Exam, String> getNotifications(DatabaseHandler database, List<Subject> subjects){
        //String query = "SELECT `exam_key`, exam.user_key, `subject_code`, `exam_date`, `difficulty_level`, `grade`, `firstname` FROM `exam` inner join `Student` on exam.user_key=Student.user_key WHERE exam_date >= DATE_ADD(CURDATE(), INTERVAL -1 DAY)";
        String query = "SELECT `exam_key`, exam.user_key, `subject_code`, `exam_date`, `difficulty_level`, `grade`, `firstname` FROM `exam` inner join `Student` on exam.user_key=Student.user_key WHERE exam_date >= DATE_ADD(CURDATE(), INTERVAL -1 DAY) AND subject_code=?";
        PreparedStatement ps;
        ResultSet rs;
        HashMap<Exam, String> notify =new HashMap<>();
        Exam temp;
        String name;
        try{
            for(Subject subjects1 : subjects){
                ps = database.getConnection().prepareStatement(query);
                ps.setInt(1, Integer.parseInt(subjects1.getSubjectCode()));
                rs = ps.executeQuery();
                while(rs.next()){
                    temp = new Exam(rs.getInt(1), rs.getInt(2), subjects1.getSubjectName(), rs.getString(5), rs.getDate(4), rs.getString(6));
                    name = rs.getString(7);
                    notify.put(temp, name);
                }
            }
            return notify;        
        }
       catch (SQLException ex) {
            Logger.getLogger(ProfessorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
        }
    
    public static HashMap<String,Integer> getGradesCount(DatabaseHandler database, List<Subject> subjects) {
             HashMap<String,Integer> map = new HashMap<String,Integer>();
             String insertquestionsquery;
        try {
            ResultSet rs;
            PreparedStatement preparedStatement;
//get the user details from the user table
            for(Subject subject : subjects){
                insertquestionsquery = "SELECT  `grade` , COUNT( * ) FROM  `exam` WHERE subject_code = ? GROUP BY  `grade` ";
                preparedStatement = database.getConnection().prepareStatement(insertquestionsquery);
                preparedStatement.setInt(1, Integer.parseInt(subject.getSubjectCode()));
                rs = preparedStatement.executeQuery();
                while (rs.next()) {
                    map.put(rs.getString(1),Integer.parseInt(rs.getString(2)));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(UserLoginTableHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
  return map;      

    }
}
