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
import qcas.Main;

/**
 * FXML Controller class
 *
 * @author RAHUL
 */
public class LoginController implements Initializable {

    @FXML
    TextField userId;
    @FXML
    TextField password;
    @FXML
    Button login;
    

    private Main application;
    
    
    public void setApp(Main application){
        this.application = application;
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        userId.setPromptText("demo");
        password.setPromptText("demo");
        
    }
    
    
    @FXML
    public void processLogin(ActionEvent event) {
        System.out.println("Test");
        System.out.println(userId.getText());
       /* if (application == null){
            // We are running in isolated FXML, possibly in Scene Builder.
            // NO-OP.
            errorMessage.setText("Hello " + userId.getText());
        } else {
            if (!application.userLogging(userId.getText(), password.getText())){
                errorMessage.setText("Username/Password is incorrect");
            }
        }*/
    }    
    
}
