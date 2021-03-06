
package qcas.views.controllers;

import com.itextpdf.text.Chapter;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javax.imageio.ImageIO;
import qcas.Constants;
import qcas.Main;
import qcas.operations.exam.Exam;
import qcas.operations.report.Report;
import qcas.operations.questions.Question;
import qcas.operations.questions.QuestionFIB;
import qcas.operations.questions.QuestionMultipleAnswer;
import qcas.operations.questions.QuestionMultipleChoice;
import qcas.operations.questions.QuestionTF;
import qcas.operations.subject.Subject;
import qcas.operations.user.User;

/**
 * FXML Controller class for student dashboard
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
    private Button submitTest;
    @FXML
    private Button nextQuestion;
    @FXML
    private Button previousQuestion;

    private int presentQuestion;
    private ArrayList<Question> quizAnswers;
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
    private Label currentQuestionNo;
    @FXML
    private Label totalQuestionNo;
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
    private int timeSeconds;
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
    private boolean quizInProgress;
    @FXML
    private Label questionDescription1;
    @FXML
    private Label questionDescription111;
    @FXML
    private BarChart<String, Integer> reportBarChart;
    @FXML
    private PieChart pieResults;
    @FXML
    private Label scoreLabel;

    private String subjectCode;
    private String difficulty;
    private int numberOfquestions;
    @FXML
    private Label passLabel;
    @FXML
    private Label failLabel;
    @FXML
    private Label gradeLabel;
    @FXML
    private Label homePrompt;
    @FXML
    private LineChart<String, Number> studentActivity;
    @FXML
    private NumberAxis yAxis;
    @FXML
    private CategoryAxis xAxis;
    private Exam exam;
    @FXML
    private Label subjectLabel;

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        clgLogo.setImage(new Image(Main.class.getResourceAsStream(Constants.clgLogo)));
        homeImg.setImage(new Image(Main.class.getResourceAsStream(Constants.homeImg)));
        quizImg.setImage(new Image(Main.class.getResourceAsStream(Constants.clipboardImg)));
        homePane.setVisible(true);

    }

    /**
     * sets the app for student dashboard
     * @param application
     */
    public void setApp(Main application) {
        this.application = application;
        loginBox.getItems().clear();
        loginBox.setPromptText(this.application.getLoggedUser().getFirstName());
        loginBox.getItems().addAll("Log Out");

        studentName.setText(this.application.getLoggedUser().getFirstName() + " " + this.application.getLoggedUser().getLastName());
        studentEmail.setText(this.application.getLoggedUser().getEmail());
        loginBox.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                if (newValue.equals("Log Out")) {
                    this.logout();
                }
            }

        });

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

        makeActivityGraph();
    }

    private void reassignDifficulty() {
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
                if (hashcountquestions.containsKey("H")) {
                    count = hashcountquestions.get("H");
                }
                if (hashcountquestions.containsKey("E")) {
                    count += hashcountquestions.get("E");
                }
                if (hashcountquestions.containsKey("M")) {
                    count += hashcountquestions.get("M");
                }
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

    private void startQuiz(ArrayList<Question> questions,String difficulty) {
        this.quizInProgress = true;
        quizcreatepane.setVisible(false);
        homePane.setVisible(false);
        
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

                    // Quiz stop code goes here
                    quizInProgress = false;
                    submitQuiz();
                }
            }
        }));
        timeline.playFromStart();
        presentQuestion = 0;
        exam = new Exam(questions,difficulty);
        quizQuestions = exam.getQuestions();
        quizAnswers = exam.getAnswers();
        previousQuestion.setDisable(true);
        nextQuestion.setDisable(false);
        questionsAttempted = new int[quizAnswers.size()];
        changeQuestion();

        this.truefalse.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (truefalse.getSelectedToggle() != null) {
                this.questionsAttempted[this.presentQuestion] = 1;
                if (truefalse.getSelectedToggle().getUserData().toString().equals("0")) {
                    ((QuestionTF) quizAnswers.get(presentQuestion)).setAnswer(true);
                } else {
                    ((QuestionTF) quizAnswers.get(presentQuestion)).setAnswer(false);
                }
            }

        });

        this.singleAnswer.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (singleAnswer.getSelectedToggle() != null) {
                this.questionsAttempted[this.presentQuestion] = 1;
                int index = Integer.parseInt(singleAnswer.getSelectedToggle().getUserData().toString());
                ((QuestionMultipleChoice) quizAnswers.get(presentQuestion)).setAnswer(index);

            }
        });

        this.fibblank.textProperty().addListener((observable, oldValue, newValue) -> {
            this.questionsAttempted[this.presentQuestion] = 1;
            ((QuestionFIB) this.quizAnswers.get(presentQuestion)).setAnswer(newValue);
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

        if (this.quizInProgress) {
            boolean submit = generateQuizInProgressAlert("Quiz in Progress. Do you want to go to profile?");
            if (submit) {
                submitQuiz();
            }
        } else {
            quizcreatepane.setVisible(false);
            quizpane.setVisible(false);
            homePane.setVisible(true);

            resultPane.setVisible(false);
            //makeActivityGraph();
        }

    }

    @FXML
    private void startPressed(ActionEvent event) {

        ArrayList<Question> questions = new ArrayList<Question>();
        int subjectCodeIndex = selectsubjectdropdown.getSelectionModel().getSelectedIndex();
        subjectCode = subjects.get(subjectCodeIndex).getSubjectCode();
        difficulty = (String) difficultyselectdropdown.getSelectionModel().getSelectedItem();
        numberOfquestions = Integer.parseInt(numberquestionsselectdropdown.getSelectionModel().getSelectedItem().toString());
        subjectLabel.setText(subjects.get(subjectCodeIndex).getSubjectName());
        totalQuestionNo.setText(numberquestionsselectdropdown.getSelectionModel().getSelectedItem().toString());

        if (subjectCode != null && difficulty != null && numberOfquestions != 0) {

            String level = "";
            switch (difficulty) {
                case "Easy":
                    level = "E";
                    break;
                case "Medium":
                    level = "M";
                    break;
                case "Hard":
                    level = "H";
                    break;
                case "Mixed":
                    level = "Mixed";
                    break;
                default:
                    break;
            }
            if (!level.equals("Mixed")) {
                questions = this.application.getQuestions(level, subjectCode, numberOfquestions);
            } else {
                questions = this.application.getQuestions(level, subjectCode, numberOfquestions, hashcountquestions.get("E"), hashcountquestions.get("M"), hashcountquestions.get("H"));
            }
            this.timeSeconds = Constants.TIME_PER_QUESTION * questions.size();
            startQuiz(questions,level);
        }

    }

    private boolean generateQuizInProgressAlert(String message) {
        boolean submitQuiz = false;
        Alert alert = new Alert(AlertType.WARNING);
        alert.setTitle("Quiz in progress...");
        alert.setHeaderText("Quiz is in progress.");

        alert.setContentText(message + " By doing so, the quiz will be submitted first.");
        ButtonType buttonTypeOne = new ButtonType("Yes,Submit");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOne, buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOne) {
            // ... user chose OK
            submitQuiz = true;
            this.quizInProgress = false;
        } else {
            // ... user chose CANCEL or closed the dialog

        }
        return submitQuiz;
    }

    @FXML
    private void submitPressed(ActionEvent event) {
        //TODO check here if a quiz is in process
        if (this.quizInProgress) {
            boolean submit = generateQuizInProgressAlert("Your time has not finished.");
            if (submit) {
                submitQuiz();

            }

        } else {
            quizpane.setVisible(false);
            resultPane.setVisible(true);
            makeActivityGraph();
        }

    }

    @FXML
    private void quizPressed(ActionEvent event) {
        if (this.quizInProgress) {
            boolean submit = generateQuizInProgressAlert("Quiz in Progress. Do you want to create another quiz?");
            if (submit) {
                submitQuiz();
            }
        } else {
            quizcreatepane.setVisible(true);
            quizpane.setVisible(false);
            homePane.setVisible(false);
            resultPane.setVisible(false);
        }

    }

    private void logout() {

        if (this.quizInProgress) {
            boolean submit = generateQuizInProgressAlert("Quiz in Progress. Are you sure you want to logout?");
            if (submit) {
                submitQuiz();
            }
            loginBox.getSelectionModel().clearSelection();
            loginBox.setPromptText(this.application.getLoggedUser().getFirstName());
        } else {
            this.application.userLogout();
        }

    }

    @FXML
    private void nextQuestionPressed(ActionEvent event) {
        if (presentQuestion <= quizAnswers.size() - 1) {
            presentQuestion = presentQuestion + 1;
            if (presentQuestion == quizAnswers.size() - 1) {
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
            if (presentQuestion <= quizAnswers.size() - 2) {
                nextQuestion.setDisable(false);
            }

            changeQuestion();

        }
    }

    private void changeQuestion() {
        Question presentQuestion = quizAnswers.get(this.presentQuestion);
        currentQuestionNo.setText(Integer.toString(this.presentQuestion + 1));
        questionDescription.setText(presentQuestion.getDescription());
        this.panefib.setVisible(false);
        this.gridpaneMA.setVisible(false);
        this.gridpaneMC.setVisible(false);
        this.gridpaneTF.setVisible(false);
        switch (presentQuestion.getType()) {
            case "FIB":
                this.panefib.setVisible(true);
                String answer = ((QuestionFIB) quizAnswers.get(this.presentQuestion)).getAnswer();
                if (this.questionsAttempted[this.presentQuestion] != 0) {
                    this.fibblank.setText(answer);
                } else {
                    this.fibblank.setText("");
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
                } else {
                    this.rbmcchoice1.setSelected(false);
                    this.rbmcchoice2.setSelected(false);
                    this.rbmcchoice3.setSelected(false);
                    this.rbmcchoice4.setSelected(false);
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
                    for (CheckBox checkbox : checkboxes) {
                        checkbox.setSelected(false);
                    }
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
                } else {
                    this.rbtftrue.setSelected(false);
                    this.rbtffalse.setSelected(false);
                }
                break;
            default:
                break;
        }
    }

    private void maAnswerChanged(int i, boolean change) {
        int[] answer = ((QuestionMultipleAnswer) this.quizAnswers.get(presentQuestion)).getAnswer();

        if (change) {
            answer[i] = 1;
        } else {
            answer[i] = 0;
        }

        ((QuestionMultipleAnswer) this.quizAnswers.get(presentQuestion)).setAnswer(answer);
    }

    private void submitQuiz() {
        //TODO evaluation code goes here
        boolean check = false;
        timeline.stop();
        presentQuestion = -1;
        Iterator it = quizAnswers.iterator();
        int i = 0;
        ArrayList<String> grade;
        
        int[] correct = new int[numberOfquestions];
        passLabel.setVisible(false);
        failLabel.setVisible(false);
        gradeLabel.setText("");
        exam.evalute(questionsAttempted);
        int totalQuestions = exam.getNumberOfQuestions();
        int correctQuestions = exam.getCorrectAnswers();
        String score = Integer.toString(correctQuestions);
        score = score + "/" + Integer.toString(totalQuestions);
        this.application.insertAnswers(quizAnswers, subjectCode, numberOfquestions, difficulty, correctQuestions, correct);

        quizpane.setVisible(false);
        resultPane.setVisible(true);

       

        scoreLabel.setText(score);
        if (exam.isPass()) {
            passLabel.setVisible(true);
        } else {
            failLabel.setVisible(true);
        }
        gradeLabel.setText(exam.getGrade());
        int correctQ = correctQuestions;
        int totalQ = totalQuestions;
        new Thread() {

            // runnable for that thread
            public void run() {
                application.insertAnswers(quizAnswers, subjectCode, numberOfquestions, difficulty, correctQ, correct);
            }

        }.start();
        
        new Thread() {

            // runnable for that thread
            public void run() {
                
                Platform.runLater(new Runnable() {
                    public void run() {
                        ObservableList<PieChart.Data> resultChart = FXCollections.observableArrayList();
                        resultChart.addAll(new PieChart.Data("Correct Answers", correctQ),
                                new PieChart.Data("Incorrect Answers", totalQ - correctQ));
                        pieResults.setAnimated(false);

                        pieResults.setData(resultChart);
                        pieResults.setLegendSide(Side.BOTTOM);
                        pieResults.setLabelsVisible(true);
                        pieResults.setStartAngle(90);
                        pieResults.setVisible(true);

                        
                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
                        // Create a custom Notification without icon
                        String filename = application.getLoggedUser().getFirstName() + "_" + dateFormat.format(exam.getExamDate()) + ".pdf";
                        Report.produceReport(exam,application.getLoggedUser(), filename, pieResults);
                        Alert alert = new Alert(AlertType.INFORMATION);
                        alert.setTitle("Report Generated");
                        alert.setHeaderText("Test Successful:");
                        alert.setContentText("Report produced at:" + filename);

                        alert.show();
                    }

                });

            }

        }.start();


    }

    private final void createReport(PieChart chart, String grade, String path) {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        File file = new File(path);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(path));
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
            com.itextpdf.text.Image graph;
            graph = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());

            document.open();
            User user = this.application.getLoggedUser();

            Paragraph title1 = new Paragraph(user.getFirstName() + " " + user.getLastName(),
                    FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLD, new CMYKColor(0, 0, 0, 0)));

            Chapter chapter1 = new Chapter(title1, 1);
            document.add(chapter1);
            document.add(new Paragraph("Email: " + user.getEmail() + "\n" + "Grades: " + grade,
                    FontFactory.getFont(FontFactory.COURIER, 14, Font.BOLD, new CMYKColor(0, 255, 0, 0))));

            document.add(graph);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     *saves the app as pdf for student dashboard
     * @param chart
     * @param path
     */
    public void saveAsPng(PieChart chart, String path) {

        WritableImage image = chart.snapshot(new SnapshotParameters(), null);

        // TODO: probably use a file chooser here
        File file = new File("chart.png");

        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
            // TODO: handle exception here
        }


    }


    


    private void makeActivityGraph() {

        LinkedHashMap activity = this.application.getStudentActivity(Integer.parseInt(this.application.getLoggedUser().getUserKey()));
        Set set = activity.entrySet();
        Iterator i = set.iterator();

        new Thread() {
            // runnable for that thread
            public void run() {
                Platform.runLater(new Runnable() {
                    public void run() {
                        XYChart.Series series = new XYChart.Series();

                        studentActivity.getData().clear();

                        while (i.hasNext()) {

                            Map.Entry me = (Map.Entry) i.next();

                            series.getData().add(new XYChart.Data(me.getKey(), me.getValue()));

                        }
                        series.setName("Tests");
                        studentActivity.setLegendVisible(true);
                        studentActivity.getData().add(series);
                    }
                });
            }
        }.start();

    }

}
