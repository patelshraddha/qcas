/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas.operations.user;

/**
 * Stores user variables and methods
 * @author Dell
 */
public class User {
    private final String id;
    private final String userKey;
    private String firstName;
    private String lastName;
    private String email;
    private String timeStamp;
    private String type;

    /**
     *Constructor for a user
     * @param id
     * @param userKey
     * @param firstName
     * @param lastName
     * @param email
     * @param timeStamp
     * @param type
     */
    public User(String id, String userKey, String firstName, String lastName, String email, String timeStamp,String type) {
        this.id = id;
        this.userKey = userKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeStamp = timeStamp;
        this.type=type;
    }

    /**
     *gets the type of the user
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *gets the ID of the user
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *gets the user key of the user
     * @return
     */
    public String getUserKey() {
        return userKey;
    }

    /**
     *gets the first name  of the user
     * @return
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     *gets the last name of the user
     * @return
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * gets the email of the user
     * @return
     */
    public String getEmail() {
        return email;
    }

    /**
     *gets the timestamp of the user
     * @return
     */
    public String getTimeStamp() {
        return timeStamp;
    }

    /**
     *sets the first name  of the user
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *sets the last name of the user
     * @param lastName
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * sets the email of the user
     * @param email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * sets timestamp for the user
     * @param timeStamp
     */
    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return "User{" + "id=" + id + ", userKey=" + userKey + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", timeStamp=" + timeStamp + ", type=" + type + '}';
    }

    /**
     *
     */
    public static void checkUsername() {}
    
}
