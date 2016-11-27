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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
public class LoginController implements Initializable {

    private Main application;
    @FXML
    private ToggleGroup signup;

    @FXML
    private TextField userPassword;
    @FXML
    private Button loginButton;
    @FXML
    private TextField userId;
    @FXML
    private ImageView clglogo;
    @FXML
    private Label invalidLabel;
    @FXML
    private Pane signUpPane;
    @FXML
    private Button registerButton;
    @FXML
    private Button signUp;
    @FXML
    private Button exit;

    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clglogo.setImage(new Image(Main.class.getResourceAsStream(Constants.clgLogo)));
    }

    @FXML
    public void processLogin(ActionEvent event) {

        
        if (application == null) {
            System.exit(0);
        } else if (!application.userLogging(userId.getText(), userPassword.getText())) {
            System.out.println("Error Logging in!!");
            invalidLabel.setVisible(true);
        }
    }
    
    @FXML
    public void processSignUp(ActionEvent event) {
        signUpPane.setVisible(true);
        
    }
    
    @FXML
    public void registerUser(ActionEvent event) {
        
        signUpPane.setVisible(false);
      
    }
    
    @FXML
    public void exitApplication(ActionEvent event)
    {
        System.exit(0);
    }
    
            

}
