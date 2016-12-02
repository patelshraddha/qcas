
package qcas.model;

import java.sql.Connection;

/**
 * DatabaseInterface interface to the database
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
