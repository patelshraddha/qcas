
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
 
    public void setUrl(String url);
    public void setUser(String user);
    public void setPassword(String password);
    public Connection getConnection();    
}
