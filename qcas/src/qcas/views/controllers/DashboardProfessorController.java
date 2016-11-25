/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.views.controllers;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import qcas.Constants;
import qcas.Main;

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
    private Pane uploadPane;
    @FXML
    private Pane reportPane;
    @FXML
    private ComboBox loginBox;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        clgLogo.setImage(new Image(getClass().getResourceAsStream(Constants.clgLogo)));
        homeImg.setImage(new Image(getClass().getResourceAsStream(Constants.homeImg)));
        uploadImg.setImage(new Image(getClass().getResourceAsStream(Constants.clipboardImg)));
        reportImg.setImage(new Image(getClass().getResourceAsStream(Constants.reportImg)));
        homePane.setVisible(true);
        reportPane.setVisible(false);
        uploadPane.setVisible(false);
        loginBox.getItems().clear();
        loginBox.getItems().addAll("Log Out");
    }    
    
    public void setApp(Main application){
        this.application = application;
    }

    @FXML
    private void homePressed(ActionEvent event) {
        homePane.setVisible(true);
        reportPane.setVisible(false);
        uploadPane.setVisible(false);
    }

    @FXML
    private void UploadPressed(ActionEvent event) {
        homePane.setVisible(false);
        reportPane.setVisible(false);
        uploadPane.setVisible(true);
    }

    @FXML
    private void reportPressed(ActionEvent event) {
        homePane.setVisible(false);
        reportPane.setVisible(true);
        uploadPane.setVisible(false);
    }
    @FXML
    private void logout(ActionEvent event) {
        //Logout
        System.exit(0);
    }
}
