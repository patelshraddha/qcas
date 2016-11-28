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
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import qcas.Constants;
import qcas.Main;
import qcas.model.SubjectHandler;
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
        professorName.setText(this.application.getLoggedUser().getFirstName());
        professorEmail.setText(this.application.getLoggedUser().getEmail());
        loginBox.setPromptText(this.application.getLoggedUser().getFirstName());

        List list = this.application.getSubjects();
        List subjectNames = new ArrayList<String>();
        for (Object subject : list) {
            subjectNames.add(((Subject) subject).getSubjectName());
        }
        subjectList.setItems(FXCollections.observableList(subjectNames));
        subjectList.setPromptText("Select Subject");
        subjects = (ArrayList<Subject>) list;
       
        subjectType.setItems(FXCollections.observableList(subjectNames));
        subjectType.setPromptText("Select Subject");
        
        reportType.getItems().clear();
        for(String s: Constants.REPORTTYPES){
            reportType.getItems().addAll(s);
        }
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
        
        
    }
}
