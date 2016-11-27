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
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;

/**
 *
 * @author Dell
 */
public class ProfessorHandler {


    
    

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
                
                ps.executeUpdate();
                n++;

            }
            return n;
        } catch (SQLException ex) {

            Logger.getLogger(ProfessorHandler.class.getName()).log(Level.SEVERE, null, ex);
        }

        return 0;
    }
}
