/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.views.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
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
import javafx.util.Duration;
import qcas.Constants;
import qcas.Main;
import qcas.operations.questions.Question;
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;
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
    @FXML
    private Label tftrue;
    @FXML
    private Label tffalse;
    
    private Timeline timeline;
    private int timeSeconds = Constants.STARTTIME;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clgLogo.setImage(new Image(Main.class.getResourceAsStream(Constants.clgLogo)));
        homeImg.setImage(new Image(Main.class.getResourceAsStream(Constants.homeImg)));        
        quizImg.setImage(new Image(Main.class.getResourceAsStream(Constants.clipboardImg)));
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
        Question question1 = new QuestionMultipleChoice("1", "MC", "E", "ssksk", "OOP", 1, "ma", "mb", "mc", "md");
        Question question2 = new QuestionMultipleAnswer("2", "MA", "E", "Question 3", "OOP", new int[]{0, 1, 0, 1}, "ma", "mb", "mc", "md");
        Question question3 = new QuestionTF("TF", "E", "Question 4", "OOP", true);
        questions.add(question);
        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        startQuiz(questions);
    }

    private void startQuiz(ArrayList<Question> questions) {
        quizcreatepane.setVisible(false);
        homePane.setVisible(false);
        
        timer.setText(Integer.toString(timeSeconds/60)+":"+Integer.toString(timeSeconds%60));
        quizpane.setVisible(true);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1),new EventHandler(){
            @Override
            public void handle(Event event) {
                timeSeconds--;
                timer.setText(Integer.toString(timeSeconds/60)+":"+Integer.toString(timeSeconds%60));
                if(timeSeconds==0){
                    timeline.stop();
                    // Quiz stop code goes here
                    //processSubmit();
                }
            }
        }));
        timeline.playFromStart();
        presentQuestion = 0;
        quizQuestions = questions;
        previousQuestion.setDisable(true);
        changeQuestion();
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
        Question presentQuestion = quizQuestions.get(this.presentQuestion);
        questionDescription.setText(presentQuestion.getDescription());
        this.panefib.setVisible(false);
        this.gridpaneMA.setVisible(false);
        this.gridpaneMC.setVisible(false);
        this.gridpaneTF.setVisible(false);
        switch (presentQuestion.getType()) {
            case "FIB":
                this.panefib.setVisible(true);
                break;
            case "MC":
                this.gridpaneMC.setVisible(true);
                ArrayList<String> mcchoices = ((QuestionMultipleChoice) presentQuestion).getChoices();
                this.mcchoice1.setText(mcchoices.get(0));
                this.mcchoice2.setText(mcchoices.get(1));
                this.mcchoice3.setText(mcchoices.get(2));
                this.mcchoice4.setText(mcchoices.get(3));
                break;
            case "MA":
                ArrayList<String> machoices = ((QuestionMultipleAnswer) presentQuestion).getChoices();
                this.machoice1.setText(machoices.get(0));
                this.machoice2.setText(machoices.get(1));
                this.machoice3.setText(machoices.get(2));
                this.machoice4.setText(machoices.get(3));
                this.gridpaneMA.setVisible(true);
                break;
            case "TF":

                this.gridpaneTF.setVisible(true);
                this.tftrue.setText("True");
                this.tffalse.setText("False");
                break;
            default:
                break;
        }
    }

}
