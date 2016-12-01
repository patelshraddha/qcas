
package qcas.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * DatabaseInterface interface to the database
 Assignment 5 Task 2 Object Oriented Programming 95-712 Class
 Assignment5_Task2
 * @author Shraddha Patel
 *
 */
public interface DatabaseInterface extends AutoCloseable{
 
    /**
     * sets url
     * @param url
     */
    public void setUrl(String url);

    /**
     * sets the user
     * @param user
     */
    public void setUser(String user);

    /**
     * sets user's password
     * @param password
     */
    public void setPassword(String password);

    /**
     * gets connection
     * @return
     */
    public Connection getConnection();    
}
