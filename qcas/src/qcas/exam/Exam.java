/*
 */
package qcas.exam;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import qcas.operations.questions.Question;

/**
 * Used to manage elements that consist within an exam 
 * @author Deepak
 */
public class Exam {
    private int exam_key;
    private int user_key;
    private ArrayList<Question> questions;
    private ArrayList<Question> answers;
    private Date examDate;             
    private int subject_code;
    private String subject;
    private int numberOfQuestions;
    private String difficulty;
    private boolean pass=false;
    private String grade="F";
    private int correctAnswers;

    /**
     * Gets all the correct answers
     * @return
     */
    public int getCorrectAnswers() {
        return correctAnswers;
    }

    /**
     *  Constructor of exam
     * @param questions
     * @param difficulty
     */
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
    
    public Exam(int exam_key, int user_key, String subject, String difficulty, Date exam_date, String grade) {
        this.exam_key = exam_key;
        this.user_key = user_key;
        this.subject = subject;
        this.difficulty = difficulty;
        this.examDate = exam_date;
        this.grade = grade;
    }
    
    /**
     * Gets questions
     * @return
     */
    public ArrayList<Question> getQuestions() {
        return questions;
    }

    /**
     * Gets answer
     * @return
     */
    public ArrayList<Question> getAnswers() {
        return answers;
    }
    
    public int getExam_key() {
        return exam_key;
    }

    public int getUser_key() {
        return user_key;
    }
    
    public int getSubject_code() {
        return subject_code;
    }

    public String getSubject() {
        return subject;
    }
    
    
    
    /**
     * Gets exam date
     * @return
     */
    public Date getExamDate() {
        return examDate;
    }

    /**
     *Gets number of questions 
     * @return
     */
    public int getNumberOfQuestions() {
        return numberOfQuestions;
    }

    /**
     *
     * @return
     */
    public String getDifficulty() {
        return difficulty;
    }

    /**
     * gets difficulty of question
     * @return
     */
    public boolean isPass() {
        return pass;
    }

    /**
     *Gets grade
     * @return
     */
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
    
    /**
     * Evaluates questions attempted
     * @param questionsAttempted
     * @return
     */
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
