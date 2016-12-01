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
    private Label signUpError;

    /**
     * Sets the app
     * @param application
     */
    public void setApp(Main application) {
        this.application = application;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        clglogo.setImage(new Image(Main.class.getResourceAsStream(Constants.clgLogo)));

        loginButton.setDefaultButton(true);
    }

    /**
     * Processes the login
     * @param event
     */
    @FXML
    public void processLogin(ActionEvent event) {

        if (application == null) {
            System.exit(0);
        } else if (!application.userLogging(userId.getText(), userPassword.getText())) {
            System.out.println("Error Logging in!!");
            invalidLabel.setVisible(true);
        }
    }

    /**
     * Processes the signup
     * @param event
     */
    @FXML
    public void processSignUp(ActionEvent event) {
        signUpPane.setVisible(true);
        signUpError.setVisible(false);
        registerEmail.clear();
        registerFirstName.clear();
        registerLastName.clear();
        registerPassword.clear();
        registerUsername.clear();
        
        List<Subject> subjects = application.getAllSubjects();
        selectMenuSubject.getItems().clear();
        for (Subject subject : subjects) {
            selectMenuSubject.getItems().add(CheckMenuItemBuilder.create()
                    .text(subject.getSubjectName())
                    .selected(false)
                    .build());

        }

    }

    /**
     * closes the signup
     * @param event
     */
    @FXML
    public void closeSignup(ActionEvent event) {
        signUpPane.setVisible(false);
    }

    /**
     * closes the register box
     * @param event
     */
    public void closeRegister(ActionEvent event) {

        signUpPane.setVisible(false);
    }

    /**
     * registers the user
     * @param event
     */
    @FXML
    public void registerUser(ActionEvent event) {
        if (registerUsername.getText().isEmpty() || registerPassword.getText().isEmpty() || registerFirstName.getText().isEmpty() || registerLastName.getText().isEmpty() || registerEmail.getText().isEmpty()) {
            signUpError.setVisible(true);
            signUpError.setText("Please complete all the fields.");
        } else {
            boolean checkPassed = true;
            int error = -1;
            // menuItem1.is
            ObservableList<MenuItem> subjects = selectMenuSubject.getItems();

            ArrayList<String> sub = new ArrayList<>();

            for (MenuItem m : subjects) {
                CheckMenuItem checkMenuItem = (CheckMenuItem) m;
                if (checkMenuItem.isSelected()) {

                    System.out.println(checkMenuItem.getText());
                    String s = checkMenuItem.getText();
                    sub.add(s);
                }
            }
            if (!emailCheck(registerEmail.getText())) {
                checkPassed = false;
                error = 1;
            }
            if (sub.size() == 0) {
                checkPassed = false;
                error = 2;
            }
            

            if (!checkPassed) {
                signUpError.setVisible(true);
                switch (error) {
                    case 1:
                        signUpError.setText("Email address is invalid.");
                        break;
                    case 2:
                        signUpError.setText("No subjects selected.");
                        break;
                    
                }

            } else {
                boolean flag = application.userRegistering(registerUsername.getText(), registerPassword.getText(), registerFirstName.getText(), registerLastName.getText(), registerEmail.getText(), sub);
                if (!flag) {
                    signUpError.setText("Opps..! You already have a account.\n Use your existing account details.");
                    signUpError.setVisible(true);
                }
                else
                    signUpPane.setVisible(false);
            }

            
        }

    }

    private static boolean emailCheck(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    @FXML
    private void exitApplication(ActionEvent event) {
        System.exit(0);
    }
}
