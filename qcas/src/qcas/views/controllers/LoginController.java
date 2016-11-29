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
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.CheckMenuItemBuilder;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import qcas.Constants;
import qcas.Main;
import qcas.operations.subject.Subject;

/**
 * FXML Controller class
 *
 * @author RAHUL
 */
public class LoginController implements Initializable {

    private Main application;

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
    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword;
    @FXML
    private TextField registerFirstName;
    @FXML
    private TextField registerLastName;
    @FXML
    private TextField registerEmail;
    @FXML
    private Label usernameError;
    @FXML
    private Label usernameError1;
    @FXML
    private Label usernameError11;
    @FXML
    private Button close;
    @FXML
    private MenuButton selectMenuSubject;
    private MenuItem menuItem1;
    @FXML
    private CheckMenuItem checkMenuSubject;
    
    
    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clglogo.setImage(new Image(Main.class.getResourceAsStream(Constants.clgLogo)));
        
        loginButton.setDefaultButton(true);
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
        
          List<Subject> subjects=application.getAllSubjects();
          
        for (Subject subject:subjects){
                selectMenuSubject.getItems().add(CheckMenuItemBuilder.create()
                .text(subject.getSubjectName())
                .selected(false)
                .build());
        
        }
        
    }
    
    @FXML
    public void closeSignup(ActionEvent event) {
        signUpPane.setVisible(false);
    }
    
    
    public void closeRegister(ActionEvent event) {
        
        
        signUpPane.setVisible(false);
    }
    
    @FXML
    public void registerUser(ActionEvent event) {
        
        signUpPane.setVisible(false);
       // menuItem1.is
        ObservableList<MenuItem> subjects = selectMenuSubject.getItems();

        
        ArrayList<String> sub = new ArrayList<>();
        
        for(MenuItem m:subjects){
            CheckMenuItem checkMenuItem = (CheckMenuItem) m;
                if(checkMenuItem.isSelected()){
                
                System.out.println(checkMenuItem.getText());
                String s= checkMenuItem.getText();
                sub.add(s);
                }
            }
      
        boolean flag=application.userRegistering(registerUsername.getText(), registerPassword.getText(), registerFirstName.getText(), registerLastName.getText(), registerEmail.getText(),sub);
      if(!flag){
          invalidLabel.setText("Opps..! You already have a account.\n Use your existing account details.");
      invalidLabel.setVisible(true);
      }
    }
    
    @FXML
    public void exitApplication(ActionEvent event)
    {
        System.exit(0);
    }
    
            

}
