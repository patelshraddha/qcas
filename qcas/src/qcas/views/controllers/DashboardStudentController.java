/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.views.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
import qcas.operations.subject.Subject;

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
    private Pane resultPane;
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
    @FXML
    private RadioButton rbmcchoice1;
    @FXML
    private RadioButton rbmcchoice2;
    @FXML
    private RadioButton rbmcchoice3;
    @FXML
    private RadioButton rbmcchoice4;
    @FXML
    private CheckBox cbmachoice1;
    @FXML
    private CheckBox cbmachoice2;
    @FXML
    private CheckBox cbmachoice3;
    @FXML
    private CheckBox cbmachoice4;
    @FXML
    private RadioButton rbtftrue;
    @FXML
    private RadioButton rbtffalse;
    @FXML
    private TextField fibblank;
    private int[] questionsAttempted;

    private Timeline timeline;
    private int timeSeconds = Constants.STARTTIME;
    @FXML
    private ComboBox<?> selectsubjectdropdown;
    private ArrayList<Subject> subjects;
    @FXML
    private Button start;
    @FXML
    private ComboBox<?> difficultyselectdropdown;
    @FXML
    private ComboBox<?> numberquestionsselectdropdown;

    private HashMap<String, Integer> hashcountquestions;

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

    }

    public void setApp(Main application) {
        this.application = application;
        studentName.setText(this.application.getLoggedUser().getFirstName());
        studentEmail.setText(this.application.getLoggedUser().getEmail());
        loginBox.setPromptText(this.application.getLoggedUser().getFirstName());

        hashcountquestions = new HashMap<String, Integer>();
        List list = this.application.getSubjects();
        List subjectNames = new ArrayList<String>();
        for (Object subject : list) {
            subjectNames.add(((Subject) subject).getSubjectName());
        }
        selectsubjectdropdown.setItems(FXCollections.observableList(subjectNames));
        
        subjects = (ArrayList<Subject>) list;
        List difficultyLevelList = new ArrayList<String>();
        difficultyLevelList.add("Easy");
        difficultyLevelList.add("Medium");
        difficultyLevelList.add("Hard");
        difficultyLevelList.add("Mixed");
        difficultyselectdropdown.setItems(FXCollections.observableList(difficultyLevelList));
        selectsubjectdropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            reassignDifficulty();
        });

        difficultyselectdropdown.valueProperty().addListener((observable, oldValue, newValue) -> {
            reassignQuestionCount(newValue);
        });
        selectsubjectdropdown.getSelectionModel().select(0);
        reassignDifficulty();
        reassignQuestionCount("Easy");

    }
    
    private void reassignDifficulty()
    {
            this.difficultyselectdropdown.getSelectionModel().select(0);
            String subjectCode = subjects.get(this.selectsubjectdropdown.getSelectionModel().getSelectedIndex()).getSubjectCode();
            hashcountquestions = this.application.getQuestionsCountDifficulty(subjectCode);
            reassignQuestionCount("Easy");
    }

    private void reassignQuestionCount(Object newValue) {
        List questionCountList = new ArrayList<String>();
        int count = 0;
        switch ((String) newValue) {
            case "Easy":
                if (hashcountquestions.containsKey("E")) {
                    count = hashcountquestions.get("E");
                }
                break;
            case "Medium":
                if (hashcountquestions.containsKey("M")) {
                    count = hashcountquestions.get("M");
                }
                break;
            case "Hard":
                if (hashcountquestions.containsKey("H")) {
                    count = hashcountquestions.get("H");
                }
                break;
            case "Mixed":
                break;
            default:
                break;
        }
        numberquestionsselectdropdown.getItems().clear();
        if (count < 5) {
            this.start.setDisable(true);
            numberquestionsselectdropdown.setItems(FXCollections.observableList(questionCountList));
        } else {
            this.start.setDisable(false);
            int i = 5;
            int x = 1;
            while (i <= count) {
                questionCountList.add(i);
                x++;
                i = 5 * x;
            }
            numberquestionsselectdropdown.setItems(FXCollections.observableList(questionCountList));
        }
        numberquestionsselectdropdown.getSelectionModel().select(0);
    }

    private void startQuiz(ArrayList<Question> questions) {
        quizcreatepane.setVisible(false);
        homePane.setVisible(false);
        questions = new ArrayList<Question>();

        Question question = new QuestionFIB("FIB", "E", "Question 1", "OOP", "ahahhaah");
        Question question1 = new QuestionMultipleChoice("1", "MC", "E", "ssksk", "OOP", 1, "ma", "mb", "mc", "md");
        Question question2 = new QuestionMultipleAnswer("2", "MA", "E", "Question 3", "OOP", new int[]{0, 1, 0, 1}, "ma", "mb", "mc", "md");
        Question question3 = new QuestionTF("TF", "E", "Question 4", "OOP", true);
        questions.add(question);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        ArrayList<Question> answers = new ArrayList<>(questions); // create a shallow copy of the questions list.
   
        

        timer.setText(Integer.toString(timeSeconds / 60) + ":" + Integer.toString(timeSeconds % 60));
        quizpane.setVisible(true);
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(1), new EventHandler() {
            @Override
            public void handle(Event event) {
                timeSeconds--;
                timer.setText(Integer.toString(timeSeconds / 60) + ":" + Integer.toString(timeSeconds % 60));
                if (timeSeconds == 0) {
                    timeline.stop();
                    // Quiz stop code goes here
                    //processSubmit();
                }
            }
        }));
        timeline.playFromStart();
        presentQuestion = 0;
        quizQuestions = answers;
        previousQuestion.setDisable(true);
        questionsAttempted = new int[quizQuestions.size()];
        changeQuestion();

        this.truefalse.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (truefalse.getSelectedToggle() != null) {
                this.questionsAttempted[this.presentQuestion] = 1;
                if (truefalse.getSelectedToggle().getUserData().toString().equals("0")) {
                    ((QuestionTF) quizQuestions.get(presentQuestion)).setAnswer(true);
                } else {
                    ((QuestionTF) quizQuestions.get(presentQuestion)).setAnswer(false);
                }
            }

        });

        this.singleAnswer.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (singleAnswer.getSelectedToggle() != null) {
                this.questionsAttempted[this.presentQuestion] = 1;
                int index = Integer.parseInt(singleAnswer.getSelectedToggle().getUserData().toString());
                ((QuestionMultipleChoice) quizQuestions.get(presentQuestion)).setAnswer(index);

            }
        });

        this.fibblank.textProperty().addListener((observable, oldValue, newValue) -> {
            this.questionsAttempted[this.presentQuestion] = 1;
            ((QuestionFIB) this.quizQuestions.get(presentQuestion)).setAnswer(newValue);
        });

        this.cbmachoice1.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.questionsAttempted[this.presentQuestion] = 1;
            maAnswerChanged(0, newValue);
        });
        this.cbmachoice2.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.questionsAttempted[this.presentQuestion] = 1;
            maAnswerChanged(1, newValue);
        });
        this.cbmachoice3.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.questionsAttempted[this.presentQuestion] = 1;
            maAnswerChanged(2, newValue);
        });
        this.cbmachoice4.selectedProperty().addListener((observable, oldValue, newValue) -> {
            this.questionsAttempted[this.presentQuestion] = 1;
            maAnswerChanged(3, newValue);
        });

    }

    @FXML
    private void homePressed(ActionEvent event) {
        //TODO check here if a quiz is in progress.
        quizcreatepane.setVisible(false);
        quizpane.setVisible(false);
        homePane.setVisible(true);
    }

    @FXML
    private void startPressed(ActionEvent event) {

        ArrayList<Question> questions = new ArrayList<Question>();

        Question question = new QuestionFIB("FIB", "E", "Question 1", "OOP", "ahahhaah");
        Question question1 = new QuestionMultipleChoice("1", "MC", "E", "ssksk", "OOP", 1, "ma", "mb", "mc", "md");
        Question question2 = new QuestionMultipleAnswer("2", "MA", "E", "Question 3", "OOP", new int[]{0, 1, 0, 1}, "ma", "mb", "mc", "md");
        Question question3 = new QuestionTF("TF", "E", "Question 4", "OOP", true);
        questions.add(question);

        questions.add(question1);
        questions.add(question2);
        questions.add(question3);
        ArrayList<Question> answers = new ArrayList<Question>(questions); // create a shallow copy of the questions list.

        startQuiz(answers);
    }

    @FXML
    private void submitPressed(ActionEvent event) {
        //TODO check here if a quiz is in process
        quizpane.setVisible(false);
        resultPane.setVisible(true);
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
                String answer = ((QuestionFIB) quizQuestions.get(this.presentQuestion)).getAnswer();
                if (this.questionsAttempted[this.presentQuestion] != 0) {
                    this.fibblank.setText(answer);
                }
                break;
            case "MC":
                this.gridpaneMC.setVisible(true);
                ArrayList<String> mcchoices = ((QuestionMultipleChoice) presentQuestion).getChoices();
                this.mcchoice1.setText(mcchoices.get(0));
                this.mcchoice2.setText(mcchoices.get(1));
                this.mcchoice3.setText(mcchoices.get(2));
                this.mcchoice4.setText(mcchoices.get(3));
                if (this.questionsAttempted[this.presentQuestion] != 0) {
                    switch (((QuestionMultipleChoice) presentQuestion).getAnswer()) {
                        case 0:
                            this.rbmcchoice1.setSelected(true);
                            break;
                        case 1:
                            this.rbmcchoice2.setSelected(true);
                            break;
                        case 2:
                            this.rbmcchoice3.setSelected(true);
                            break;
                        case 3:
                            this.rbmcchoice4.setSelected(true);
                            break;

                    }
                }
                this.rbmcchoice1.setUserData(0);
                this.rbmcchoice2.setUserData(1);
                this.rbmcchoice3.setUserData(2);
                this.rbmcchoice4.setUserData(3);
                break;
            case "MA":
                ArrayList<String> machoices = ((QuestionMultipleAnswer) presentQuestion).getChoices();
                this.machoice1.setText(machoices.get(0));
                this.machoice2.setText(machoices.get(1));
                this.machoice3.setText(machoices.get(2));
                this.machoice4.setText(machoices.get(3));
                int[] answers = ((QuestionMultipleAnswer) presentQuestion).getAnswer();
                CheckBox[] checkboxes = new CheckBox[]{this.cbmachoice1, this.cbmachoice2, this.cbmachoice3, this.cbmachoice4};
                int i = 0;
                if (this.questionsAttempted[this.presentQuestion] != 0) {

                    for (CheckBox checkbox : checkboxes) {
                        if (answers[i] == 0) {
                            checkbox.setSelected(false);
                        } else {
                            checkbox.setSelected(true);
                        }
                        i++;
                    }
                } else {
                    ((QuestionMultipleAnswer) presentQuestion).setAnswer(new int[]{0, 0, 0, 0});
                }
                this.gridpaneMA.setVisible(true);
                break;
            case "TF":
                this.gridpaneTF.setVisible(true);
                this.tftrue.setText("True");
                this.tffalse.setText("False");
                this.rbtftrue.setUserData(0);
                this.rbtffalse.setUserData(1);
                if (this.questionsAttempted[this.presentQuestion] != 0) {
                    if (((QuestionTF) presentQuestion).getAnswer()) {
                        this.rbtftrue.setSelected(true);
                    } else {
                        this.rbtffalse.setSelected(true);
                    }
                }
                break;
            default:
                break;
        }
    }

    private void maAnswerChanged(int i, boolean change) {
        int[] answer = ((QuestionMultipleAnswer) this.quizQuestions.get(presentQuestion)).getAnswer();

        if (change) {
            answer[i] = 1;
        } else {
            answer[i] = 0;
        }

        ((QuestionMultipleAnswer) this.quizQuestions.get(presentQuestion)).setAnswer(answer);
    }

}
