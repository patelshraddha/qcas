/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.views.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import qcas.Constants;
import qcas.Main;
import qcas.operations.questions.Question;
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.user.User;

/**
 * FXML Controller class
 *
 * @author RAHUL
 */
public class DashboardStudentController implements Initializable {

    private Main application;
    @FXML
    private ToggleGroup singleAnswer;
    @FXML
    private ImageView clgLogo;
    @FXML
    private ImageView homeImg;
    @FXML
    private ImageView quizImg;
    @FXML
    private Button homeButton;
    @FXML
    private Button quizButton;
    @FXML
    private Pane homePane;

    @FXML
    private ComboBox loginBox;
    @FXML
    private Label studentName;
    @FXML
    private Label studentEmail;
    @FXML
    private Pane quizcreatepane;
    @FXML
    private Pane quizpane;
    @FXML
    private Label questionDescription;
    @FXML
    private Label timer;
    @FXML
    private ProgressBar progressbar;
    @FXML
    private Button submitTest;
    @FXML
    private Button nextQuestion;
    @FXML
    private Button previousQuestion;
    private int presentQuestion;
    private ArrayList<Question> quizQuestions;
    @FXML
    private GridPane gridpaneMC;
    @FXML
    private Label mcchoice1;
    @FXML
    private Label mcchoice2;
    @FXML
    private Label mcchoice3;
    @FXML
    private Label mcchoice4;
    @FXML
    private GridPane gridpaneMA;
    @FXML
    private Label machoice1;
    @FXML
    private Label machoice2;
    @FXML
    private Label machoice3;
    @FXML
    private Label machoice4;
    @FXML
    private GridPane gridpaneTF;
    @FXML
    private Pane panefib;
    @FXML
    private ToggleGroup truefalse;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clgLogo.setImage(new Image(getClass().getResourceAsStream(Constants.clgLogo)));
        homeImg.setImage(new Image(getClass().getResourceAsStream(Constants.homeImg)));
        quizImg.setImage(new Image(getClass().getResourceAsStream(Constants.clipboardImg)));
        loginBox.getItems().clear();
        loginBox.getItems().addAll("Log Out");
        homePane.setVisible(true);
        quizcreatepane.setVisible(false);
    }

    public void setApp(Main application) {
        this.application = application;
        studentName.setText(this.application.getLoggedUser().getFirstName());
        studentEmail.setText(this.application.getLoggedUser().getEmail());
        loginBox.setPromptText(this.application.getLoggedUser().getFirstName());
        ArrayList<Question> questions = new ArrayList<Question>();
        Question question = new QuestionFIB("FIB", "E", "Question 1", "OOP", "ahahhaah");
        Question question1 = new QuestionFIB("MC", "H", "Question 2", "OOP", "ahahhaah");
        Question question2 = new QuestionFIB("FIB", "E", "Question 3", "OOP", "ahahhaah");
        Question question3 = new QuestionFIB("FIB", "E", "Question 4", "OOP", "ahahhaah");
        questions.add(question);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        startQuiz(questions);
    }

    private void startQuiz(ArrayList<Question> questions) {
        quizcreatepane.setVisible(false);
        homePane.setVisible(false);
        quizpane.setVisible(true);
        presentQuestion = 0;
        quizQuestions = questions;
        previousQuestion.setDisable(true);
        questionDescription.setText(questions.get(presentQuestion).getDescription());
    }

    @FXML
    private void homePressed(ActionEvent event) {
        //TODO check here if a quiz is in progress.
        homePane.setVisible(true);
        quizcreatepane.setVisible(false);
    }

    @FXML
    private void quizPressed(ActionEvent event) {
        //TODO check here if a quiz is in process
        homePane.setVisible(false);
        quizcreatepane.setVisible(true);
    }

    @FXML
    private void logout(ActionEvent event) {
        //Logout
        this.application.userLogout();
    }

    @FXML
    private void fibtextchanged(InputMethodEvent event) {
    }

    @FXML
    private void nextQuestionPressed(ActionEvent event) {
        if (presentQuestion <= quizQuestions.size() - 1) {
            presentQuestion = presentQuestion + 1;
            if (presentQuestion == quizQuestions.size() - 1) {
                nextQuestion.setDisable(true);
            }
            if (presentQuestion >= 1) {
                previousQuestion.setDisable(false);
            }
            changeQuestion();
            
        }
    }

    @FXML
    private void previousQuestionPressed(ActionEvent event) {
        if (presentQuestion > 0) {
            presentQuestion = presentQuestion - 1;
            if (presentQuestion == 0) {
                previousQuestion.setDisable(true);
            }
            if (presentQuestion <= quizQuestions.size() - 2) {
                nextQuestion.setDisable(false);
            }
            changeQuestion();
            
        }
    }

    private void changeQuestion() {
       questionDescription.setText(quizQuestions.get(presentQuestion).getDescription());
    }

}
