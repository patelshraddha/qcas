/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package qcas;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import qcas.exam.Exam;
import qcas.model.CSVReader;
import qcas.model.DatabaseHandler;
import qcas.model.ProfessorHandler;
import qcas.model.SubjectHandler;
import qcas.model.StudentHandler;
import qcas.model.UserLoginTableHandler;
import qcas.operations.questions.Question;
import qcas.operations.subject.Subject;
import qcas.operations.user.User;
import qcas.views.controllers.DashboardProfessorController;
import qcas.views.controllers.DashboardStudentController;
import qcas.views.controllers.LoginController;
import qcas.model.UserRegisterTableHandler;
/**
 * Main Application. This class handles navigation and user session.
 */
public class Main extends Application {

    private Stage stage;
    private DatabaseHandler database;
    private User loggedUser = null;
    private final double MINIMUM_WINDOW_WIDTH = 390.0;
    private final double MINIMUM_WINDOW_HEIGHT = 500.0;

    /**
     * This class handles navigation and user session. 
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(Main.class, (java.lang.String[]) null);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            database = new DatabaseHandler(Constants.DATABASEDRIVER + Constants.DATABASEURL, Constants.USERNAME, Constants.USERPASSWORD);

        } catch (SQLException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Could not connect ");
            return;
        }
        try {
            stage = primaryStage;
            stage.setResizable(false);
            stage.setTitle("Quiz Creation and Assessment System");
            stage.getIcons().add(new Image(Main.class.getResourceAsStream(Constants.cmuIconImg)));
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            gotoLogin();
            stage.show();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the logged in user
     * @return
     */
    public User getLoggedUser() {
        return loggedUser;
    }

    /**
     * Used to Log in a user
     * @param userId
     * @param password
     * @return If login is successfull 
     */
    public boolean userLogging(String userId, String password) {
        if (UserLoginTableHandler.verifyLogin(this.database, userId, password)) {
            loggedUser = UserLoginTableHandler.getUser(database, userId);
            
            gotoProfile();
            return true;
        } else {
            return false;
        }

    }

    /**
     * Registers the user to the database
     * @param userName
     * @param password
     * @param firstName
     * @param secondName
     * @param emailID
     * @param subjectMenuList
     * @return Returns Boolean if the user is registered successfully or not
     */
    public boolean userRegistering(String userName, String password,String firstName, String secondName,String emailID,ArrayList<String> subjectMenuList) {
	        if (!UserRegisterTableHandler.isUsernamePresent(this.database, userName)) {
	            String userId=UserRegisterTableHandler.saveUser(database, userName, password, firstName, secondName, emailID,subjectMenuList);
	            loggedUser = UserLoginTableHandler.getUser(database, userId);
                    //System.out.println(loggedUser);
                    gotoProfile();
	            return true;
	        }
	        else{
                    
                    return false;}
	    }
	    
    /**
     * Logs out the user
     */
    public void userLogout() {
        loggedUser = null;
        gotoLogin();
    }

    private void gotoProfile() {
        try {

            if (loggedUser.getType().equals(Constants.PROFESSORTYPE)) {

                DashboardProfessorController dashboard = (DashboardProfessorController) replaceSceneContent(Constants.PROFESSORDASHBOARDFXML);
                dashboard.setApp(this);
            } else if (loggedUser.getType().equals(Constants.STUDENTTYPE)) {
                DashboardStudentController dashboard = (DashboardStudentController) replaceSceneContent(Constants.STUDENTDASHBOARDFXML);
                dashboard.setApp(this);
            }
            //ProfileController profile = (ProfileController) replaceSceneContent("profile.fxml");
            //profile.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the stage that is built using FXML
     * @return
     */
    public Stage getStage() {
        return this.stage;
    }

    private void gotoLogin() {
        try {
            LoginController login = (LoginController) replaceSceneContent(Constants.LOGINSCREENFXML);
            login.setApp(this);
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private Initializable replaceSceneContent(String fxml) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        InputStream in = Main.class.getResourceAsStream(fxml);
        loader.setBuilderFactory(new JavaFXBuilderFactory());
        loader.setLocation(Main.class.getResource(fxml));
        Parent page;
        try {
            page = loader.load(in);
        } finally {
            in.close();
        }
        Scene scene = new Scene(page);
        stage.setScene(scene);
        stage.sizeToScene();
        return (Initializable) loader.getController();
    }

    /**
     * Uploads the csv question file 
     * @param file
     * @param subject
     * @return Number of questions updated
     */
    public int uploadFile(File file, String subject) {
        CSVReader reader = new CSVReader(file, subject);
        if (reader.ParseCSV()) {
            ArrayList<Question> questions = reader.getQuestions();

            if (questions.size() == 0) {
                return 0;
            } else {

                int noOfquestions;
                noOfquestions = ProfessorHandler.insertQuestions(database, questions);
                return noOfquestions;
            }
        } else {
            return -1;
        }
    }
    
    /**
     * gets Student activity 
     * @param user_id
     * @return
     */
    public LinkedHashMap getStudentActivity(int user_id){
        return StudentHandler.getStudentActivity(this.database, user_id);
    }

    /** Gets all the subjects of a user
     *
     * @return list subjects of a user
     */
    public List<Subject> getSubjects() {

        return SubjectHandler.getSubjectUser(this.database, this.getLoggedUser());

    }

    /**
     * Gets all the subjects 
     * @return all the subjects 
     */
    public List<Subject> getAllSubjects() {
        return SubjectHandler.getAllSubjects(this.database);
    }

    /**
     *gets Question's Count with its Difficulty
     * @param subjectCode
     * @return hashmap with Question's Count with its Difficulty
     */
    public HashMap<String, Integer> getQuestionsCountDifficulty(String subjectCode) {
        return StudentHandler.getCountQuestions(this.database, subjectCode);
    }
    
    /**
     * Gets test taken for a particular sunject code
     * @param subjectCode
     * @return list of test taken for a particular sunject code
     */
    public ArrayList<Integer> getTestsTaken(int subjectCode){
        return ProfessorHandler.getTestsTaken(database, subjectCode);
    }
    
    /**
     * Gets test taken for a particular sunject code over a time period
     * @param subjectCode
     * @return list of test taken for a particular sunject code over a time period
     */
    public ArrayList<Integer> getResultOverTime(int subjectCode){
        return ProfessorHandler.getResultOverTime(database, subjectCode);
    }
    
    /**
     * Gets average scores of a subject code
     * @param subjectCode
     * @return arraylist of average scores of a subject code
     */
    public ArrayList<Double> getAverageScores(int subjectCode){
        return ProfessorHandler.getAverageScores(database, subjectCode);
    }
    
    /**
     * Gets score level according to subject code
     * @param subjectCode
     * @return returns arraylist score level according to subject code
     */
    public ArrayList<Double> getScoresLevel(int subjectCode){
        return ProfessorHandler.getScoresLevel(database, subjectCode);
    }

    /**
     * Gets questions according to the required level, subject and counts of questions
     * @param level
     * @param subjectCode
     * @param counts
     * @return arraylist questions according to the required level, subject and counts of questions
     */
    public ArrayList<Question> getQuestions(String level, String subjectCode, int... counts) {

        HashMap<String, Integer> hm = new HashMap<String, Integer>();
        hm.put("H", 0);
        hm.put("M", 0);
        hm.put("E", 0);
        if (level.equals("Mixed")) {           
            hm.put("H", counts[3]);
            hm.put("M", counts[2]);
            hm.put("E", counts[1]);
            hm = calculateCount(hm,counts[0]); //counts[0] total number of questions to be fetched.
        } else {
            hm.put(level, counts[0]);
        }

        ArrayList<Question> questions = StudentHandler.getQuestions(database, subjectCode, hm);

        return questions;
    }

    /**
     * Calculates numbers of easy medium and hard questions 
     * @param map
     * @param max
     * @return
     */
    public static HashMap<String, Integer> calculateCount(HashMap<String, Integer> map, int max) {

        HashMap<String, Integer> countMap = new HashMap<String, Integer>();
        countMap.put("E", 0);
        countMap.put("M", 0);
        countMap.put("H", 0);

        HashMap<String, Double> probMap = new HashMap<String, Double>();
        probMap.put("E", 0.45);
        probMap.put("M", 0.35);
        probMap.put("H", 0.2);

        RandomCollection<String> x = new RandomCollection<String>();
        for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
            String p = it.next();
            x.add(probMap.get(p), p);
        }

        for (int i = 0; i < max; i++) {
            String ch = x.next();
            countMap.put(ch, countMap.get(ch) + 1);

            int count = map.get(ch);
            if (count - 1 != 0) {
                map.put(ch, count - 1);
            } else {
                map.remove(ch);
                x.remove();
                for (Iterator<String> it = map.keySet().iterator(); it.hasNext();) {
                    String p = it.next();
                    x.add(probMap.get(p), p);
                }
            }

        }
        return countMap;
    }
    
    /**
     * Inserts answers to the database
     * @param quizAnswers
     * @param subjectCode
     * @param noQuestions
     * @param diff
     * @param correctQuestions
     * @param isCorrect
     * @return
     */
    public int insertAnswers(ArrayList<Question> quizAnswers, String subjectCode, int noQuestions, String diff, int correctQuestions, int[] isCorrect){
        return StudentHandler.insertSelection(database, getLoggedUser(), quizAnswers, subjectCode, noQuestions, diff, correctQuestions, isCorrect);
    }

    public HashMap<Exam, String> getNotifications(){
        return ProfessorHandler.getNotifications(database, getSubjects());
    }

    public HashMap<String,Integer> getGradesCount() {
        HashMap<String,Integer> map = ProfessorHandler.getGradesCount(database, getSubjects());
        return map;
    }
    

}
