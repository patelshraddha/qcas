/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.views.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import qcas.Constants;
import qcas.Main;
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
    private ToggleGroup singleAnswer1;
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
    private Pane quizPane;
    @FXML
    private ComboBox loginBox;


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
        
    } 
    
    public void setApp(Main application){
        this.application = application;
    }

    @FXML
    private void homePressed(ActionEvent event) {
        //TODO check here if a quiz is in progress.
        homePane.setVisible(true);
        quizPane.setVisible(false);
    }

    @FXML
    private void quizPressed(ActionEvent event) {
        //TODO check here if a quiz is in process
        homePane.setVisible(false);
        quizPane.setVisible(true);
    }
    
    @FXML
    private void logout(ActionEvent event) {
        //Logout
        System.exit(0);
    }
    
}
