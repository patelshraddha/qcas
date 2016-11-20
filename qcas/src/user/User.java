/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package user;

/**
 *
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

    public User(String id, String userKey, String firstName, String lastName, String email, String timeStamp,String type) {
        this.id = id;
        this.userKey = userKey;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeStamp = timeStamp;
        this.type=type;
    }

    public String getType() {
        return type;
    }

 
    
    public String getId() {
        return id;
    }

    public String getUserKey() {
        return userKey;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }


    
}
