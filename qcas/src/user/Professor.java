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
public class Professor extends User{
    
       public Professor(String id, String userKey, String firstName, String lastName, String email, String timeStamp) {
        super(id, userKey);
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.timeStamp = timeStamp;
    }
       
}
