/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.views.controllers;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import qcas.Constants;
import qcas.Main;
import qcas.operations.subject.Subject;

/**
 * FXML Controller class
 *
 * @author RAHUL
 */
public class DashboardProfessorController implements Initializable {

    private Main application;
    @FXML
    private ImageView clgLogo;
    @FXML
    private ImageView homeImg;
    @FXML
    private ImageView uploadImg;
    @FXML
    private ImageView reportImg;
    @FXML
    private Button homeButton;
    @FXML
    private Button uploadButton;
    @FXML
    private Button reportButton;
    @FXML
    private Pane homePane;
    @FXML
    private Label professorName;
    @FXML
    private Label professorEmail;
    @FXML
    private Pane uploadPane;
    @FXML
    private Pane reportPane;
    @FXML
    private ComboBox loginBox;
    @FXML
    private Button selectFile;
    @FXML
    private Label selectfilename;

    private File file = null;
    @FXML
    private Button uploadFileButton;
    private ArrayList<Subject> subjects;
    @FXML
    private Label fileuploadalert;

    @FXML
    private ComboBox subjectList;
    @FXML
    private ComboBox reportType;
    @FXML
    private ComboBox subjectType;
    @FXML
    private Button generate;
    @FXML
    private BarChart<String, Number> testsTakenChart;
    @FXML
    private final NumberAxis yAxis = new NumberAxis();
    @FXML
    private final CategoryAxis xAxis = new CategoryAxis();
    @FXML
    private BarChart<String, Number> testsTakenChart1;
    @FXML
    private NumberAxis yAxis1 = new NumberAxis();
    @FXML
    private CategoryAxis xAxis1 = new CategoryAxis();
    @FXML
    private BarChart<String, Number> testsTakenChart2;
    @FXML
    private NumberAxis yAxis2;
    @FXML
    private CategoryAxis xAxis2;
    @FXML
    private BarChart<String, Number> testsTakenChart3;
    @FXML
    private NumberAxis yAxis3;
    @FXML
    private CategoryAxis xAxis3;
    private ArrayList<String> subjectNames;
    private List<Subject> list;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        clgLogo.setImage(new Image(Main.class.getResourceAsStream(Constants.clgLogo)));
        homeImg.setImage(new Image(Main.class.getResourceAsStream(Constants.homeImg)));
        uploadImg.setImage(new Image(Main.class.getResourceAsStream(Constants.clipboardImg)));
        reportImg.setImage(new Image(Main.class.getResourceAsStream(Constants.reportImg)));
        homePane.setVisible(true);

        reportPane.setVisible(false);
        uploadPane.setVisible(false);
        loginBox.getItems().clear();
        loginBox.getItems().addAll("Log Out");

    }

    public void setApp(Main application) {
        this.application = application;
        professorName.setText(this.application.getLoggedUser().getFirstName() + " " + this.application.getLoggedUser().getLastName());
        professorEmail.setText(this.application.getLoggedUser().getEmail());
        loginBox.setPromptText(this.application.getLoggedUser().getFirstName());

        list = this.application.getSubjects();
        subjectNames = new ArrayList<String>();
        for (Object subject : list) {
            subjectNames.add(((Subject) subject).getSubjectName());
        }
        subjectList.setItems(FXCollections.observableList(subjectNames));
        subjectList.setPromptText("Select Subject");
        subjects = (ArrayList<Subject>) list;

    }

    @FXML
    private void homePressed(ActionEvent event) {
        homePane.setVisible(true);
        reportPane.setVisible(false);
        uploadPane.setVisible(false);
        this.fileuploadalert.setText("");
    }

    @FXML
    private void UploadPressed(ActionEvent event) {
        homePane.setVisible(false);
        reportPane.setVisible(false);
        uploadPane.setVisible(true);
        this.fileuploadalert.setText("");
    }

    @FXML
    private void reportPressed(ActionEvent event) {
        homePane.setVisible(false);
        reportPane.setVisible(true);
        uploadPane.setVisible(false);
        this.fileuploadalert.setText("");
        subjectType.setItems(FXCollections.observableList(subjectNames));
        subjectType.setPromptText("Select Subject");

        subjectType.getSelectionModel().select(0);
        reportType.getItems().clear();
        for (String s : Constants.REPORTTYPES) {
            reportType.getItems().addAll(s);
        }

        subjectType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                reportType.getSelectionModel().select(0);

            }
        });

        reportType.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                makeReport(newValue.toString());
            }
        });
        reportType.getSelectionModel().select(0);

    }

    @FXML
    private void logout(ActionEvent event) {
        //Logout
        this.application.userLogout();
    }

    @FXML
    private void openSelectFile(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        file = fileChooser.showOpenDialog(this.application.getStage());
        if (file != null) {
            this.selectfilename.setText(file.getName());
            this.fileuploadalert.setText("");
        }

    }

    @FXML
    private void uploadFile(ActionEvent event) {
        if (this.file != null) {
            Subject selectedSubject = subjects.get(this.subjectList.getSelectionModel().getSelectedIndex());
            int questions = this.application.uploadFile(this.file, selectedSubject.getSubjectCode());
            switch (questions) {
                case -1:
                    this.fileuploadalert.setText("File isn't in a proper format. Please try again.");
                    break;
                case 0:
                    this.fileuploadalert.setText("Questions could not be uploaded.Please try again.");
                    break;
                default:
                    this.fileuploadalert.setText(questions + " questions uploaded to the database.");
                    file = null;
                    this.selectfilename.setText("");
                    break;
            }
        } else {
            this.fileuploadalert.setText("You haven't selected any file.");
        }
    }

    @FXML
    private void generateReport(ActionEvent event) {

        Subject selectedSubject = subjects.get(this.subjectType.getSelectionModel().getSelectedIndex());

        if (reportType.getSelectionModel().getSelectedItem().toString().equals(Constants.REPORTTYPES[0])) {                       //First Report selected
            ArrayList<Integer> testsCount = this.application.getTestsTaken(Integer.parseInt(selectedSubject.getSubjectCode()));
            makeTestsTakenChart(testsCount);
        } else if (reportType.getSelectionModel().getSelectedItem().toString().equals(Constants.REPORTTYPES[1])) {                       //Second Report selected
            ArrayList<Double> avgScores = this.application.getAverageScores(Integer.parseInt(selectedSubject.getSubjectCode()));
            makeAvgScoresChart(avgScores);
        } else if (reportType.getSelectionModel().getSelectedItem().toString().equals(Constants.REPORTTYPES[2])) {
            ArrayList<Double> avgLevelScores = this.application.getScoresLevel(Integer.parseInt(selectedSubject.getSubjectCode()));
            makeScoresLevelChart(avgLevelScores);
        } else if (reportType.getSelectionModel().getSelectedItem().toString().equals(Constants.REPORTTYPES[3])) {
            ArrayList<Integer> resultCount = this.application.getResultOverTime(Integer.parseInt(selectedSubject.getSubjectCode()));
            makeResultsChart(resultCount);
        }

    }

    private void makeReport(String report) {
        Subject selectedSubject = subjects.get(subjectType.getSelectionModel().getSelectedIndex());

        if (report.equals(Constants.REPORTTYPES[0])) {                       //First Report selected
            new Thread() {
                // runnable for that thread
                public void run() {
                    ArrayList<Integer> testsCount = application.getTestsTaken(Integer.parseInt(selectedSubject.getSubjectCode()));
                    Platform.runLater(new Runnable() {
                        public void run() {
                             makeTestsTakenChart(testsCount);
                        }
                    });
                }
            }.start();
            
            
        } else if (report.equals(Constants.REPORTTYPES[1])) {                       //Second Report selected
            new Thread() {
                // runnable for that thread
                public void run() {
                    ArrayList<Double> avgScores = application.getAverageScores(Integer.parseInt(selectedSubject.getSubjectCode()));
                    Platform.runLater(new Runnable() {
                        public void run() {
                             makeAvgScoresChart(avgScores);
                        }
                    });
                }
            }.start();
            
            
        } else if (report.equals(Constants.REPORTTYPES[2])) {
            
            new Thread() {
                // runnable for that thread
                public void run() {
                    ArrayList<Double> avgLevelScores = application.getScoresLevel(Integer.parseInt(selectedSubject.getSubjectCode()));
           
                    Platform.runLater(new Runnable() {
                        public void run() {
                            makeScoresLevelChart(avgLevelScores);
                        }
                    });
                }
            }.start();
            
            
        } else if (report.equals(Constants.REPORTTYPES[3])) {
             new Thread() {

                // runnable for that thread
                public void run() {
                    ArrayList<Integer> resultCount = application.getResultOverTime(Integer.parseInt(selectedSubject.getSubjectCode()));
                    Platform.runLater(new Runnable() {
                        public void run() {
                            makeResultsChart(resultCount);
                        }
                    });
                }
            }.start();
        }

    }

    private void makeTestsTakenChart(ArrayList<Integer> testsCount) {
        testsTakenChart1.setVisible(false);
        testsTakenChart2.setVisible(false);
        testsTakenChart3.setVisible(false);

        testsTakenChart.getData().clear();
        testsTakenChart.setVisible(true);

        XYChart.Series series1 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data("Past Month", testsCount.get(0)));
        series1.getData().add(new XYChart.Data("Past Quarter", testsCount.get(1)));
        series1.getData().add(new XYChart.Data("Past Year", testsCount.get(2)));
        series1.setName("Number of tests");

        testsTakenChart.getData().addAll(series1);

    }

    private void makeAvgScoresChart(ArrayList<Double> avgScores) {
        testsTakenChart.setVisible(false);
        testsTakenChart2.setVisible(false);
        testsTakenChart3.setVisible(false);

        testsTakenChart1.getData().clear();
        testsTakenChart1.setVisible(true);

        XYChart.Series series1 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data("Past Month", avgScores.get(0)));
        series1.getData().add(new XYChart.Data("Past Quarter", avgScores.get(1)));
        series1.getData().add(new XYChart.Data("Past Year", avgScores.get(2)));
        series1.setName("Average Scores");

        testsTakenChart1.getData().addAll(series1);

    }

    private void makeScoresLevelChart(ArrayList<Double> avgLevelScores) {
        testsTakenChart1.setVisible(false);
        testsTakenChart.setVisible(false);
        testsTakenChart3.setVisible(false);

        testsTakenChart2.getData().clear();
        testsTakenChart2.setVisible(true);

        XYChart.Series series1 = new XYChart.Series<>();

        series1.getData().add(new XYChart.Data("Easy", avgLevelScores.get(0)));
        series1.getData().add(new XYChart.Data("Medium", avgLevelScores.get(1)));
        series1.getData().add(new XYChart.Data("Hard", avgLevelScores.get(2)));
        series1.getData().add(new XYChart.Data("Mixed", avgLevelScores.get(3)));
        series1.setName("Average Scores");

        testsTakenChart2.getData().addAll(series1);

    }

    private void makeResultsChart(ArrayList<Integer> resultCount) {
        testsTakenChart1.setVisible(false);
        testsTakenChart2.setVisible(false);
        testsTakenChart.setVisible(false);

        testsTakenChart3.getData().clear();
        testsTakenChart3.setVisible(true);

        XYChart.Series series1 = new XYChart.Series<>();
        series1.getData().add(new XYChart.Data("Pass", resultCount.get(0)));
        series1.getData().add(new XYChart.Data("Fail", resultCount.get(1)));
        series1.setName("Past Month");

        XYChart.Series series2 = new XYChart.Series<>();
        series2.getData().add(new XYChart.Data("Pass", resultCount.get(2)));
        series2.getData().add(new XYChart.Data("Fail", resultCount.get(3)));
        series2.setName("Past Quarter");

        XYChart.Series series3 = new XYChart.Series<>();
        series3.getData().add(new XYChart.Data("Pass", resultCount.get(4)));
        series3.getData().add(new XYChart.Data("Fail", resultCount.get(5)));
        series3.setName("Past Year");

        testsTakenChart3.getData().addAll(series1, series2, series3);

    }
}
