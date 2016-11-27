/*
 * Copyright (c) 2012, Oracle and/or its affiliates. All rights reserved.
 */
package qcas;

import java.io.File;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.scene.Parent;
import qcas.model.CSVReader;
import qcas.model.DatabaseHandler;
import qcas.model.ProfessorHandler;
import qcas.model.SubjectHandler;
import qcas.model.UserLoginTableHandler;
import qcas.operations.questions.Question;
import qcas.operations.subject.Subject;
import qcas.operations.user.User;
import qcas.views.controllers.DashboardProfessorController;
import qcas.views.controllers.DashboardStudentController;
import qcas.views.controllers.LoginController;

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
            stage.setTitle("Quiz Creation and Assessment System");
            stage.setMinWidth(MINIMUM_WINDOW_WIDTH);
            stage.setMinHeight(MINIMUM_WINDOW_HEIGHT);
            gotoLogin();
            stage.show();

        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    public boolean userLogging(String userId, String password) {
        if (UserLoginTableHandler.verifyLogin(this.database, userId, password)) {
            loggedUser = UserLoginTableHandler.getUser(database, userId);
            gotoProfile();
            return true;
        } else {
            return false;
        }

    }

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

    public List<Subject> getSubjects() {

        return SubjectHandler.getSubjectUser(this.database, this.getLoggedUser());

    }

    public List<Subject> getAllSubjects() {
        return SubjectHandler.getAllSubjects(this.database);
    }

}
