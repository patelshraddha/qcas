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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
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
    
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
    }
    
    
    @FXML
    public void processLogin(ActionEvent event) {
        
       System.out.println(userId.getText());
       if (application == null){
            System.exit(0);
        } else {
            if (!application.userLogging(userId.getText(), userPassword.getText())){
                // error message displayed
                //errorMessage.setText("Username/Password is incorrect");
                System.out.println("Error Logging in!!");
            }
        }
    }    
    
}
