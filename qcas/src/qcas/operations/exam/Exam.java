/*
 */
package qcas.operations.exam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import qcas.operations.questions.Question;

/**
 *
 * @author Deepak
 */
public class Exam {
    private ArrayList<Question> questions;
    private ArrayList<Question> answers;
    private Date examDate;             
    
    private int numberOfQuestions;
    private String difficulty;
    private boolean pass=false;
    private String grade="F";
    private int correctAnswers;

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public Exam(ArrayList<Question> questions, String difficulty) {
        this.questions = questions;
        answers = new ArrayList<>();// = new ArrayList<Question>(questions); // create a shallow copy of the questions list.
        for (Question question : questions) {
            answers.add(question.clone());
        }
        this.numberOfQuestions = questions.size();
        this.difficulty = difficulty;
        this.examDate = new Date();
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public ArrayList<Question> getAnswers() {
        return answers;
    }

    public Date getExamDate() {
        return examDate;
    }

    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public boolean isPass() {
        return pass;
    }

    public String getGrade() {
        return grade;
    }
    
    private void generateGrade(int correctQuestions) {
        double percent = (correctQuestions / numberOfQuestions) * 100;
        if (percent >= 60) {
            pass = true;
        } else {
            pass = false;
        }

        if (percent < 60 && percent >= 0) {
            grade="F";
        } else if (percent >= 60 && percent < 70) {
            grade="C";
        } else if (percent >= 70 && percent < 80) {
            grade="B";
        } else if (percent >= 80 && percent < 90) {
            grade="B+";
        } else if (percent >= 90 && percent < 100) {
            grade="A";
        } else if (percent == 100) {
           grade="A+";
        }
    }
    
    
    public int evalute(int[] questionsAttempted)
    {
        correctAnswers=0;
        Iterator it = answers.iterator();
        int i=0;
        for (Question quizQuestion : questions) {
            if (questionsAttempted[i] != 0) {
                if (quizQuestion.evaluate((Question) it.next())) {
                    correctAnswers++;                            
                }
            }
            i++;
        }
        generateGrade(correctAnswers);
        return correctAnswers;
    }
    
   
}
