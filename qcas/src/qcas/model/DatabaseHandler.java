package qcas.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * DatabaseHandler class to implement the database interface
 * @author Shraddha Patel
 *
 */
public class DatabaseHandler implements DatabaseInterface {

    
    private String url = "";
    private String username = "";
    private String password = "";
    private Connection con;
    private Statement statement;

    /**
     * Constructor method
     * @param url url of the database
     * @param username username
     * @param password password
     * @throws SQLException throws exception for SQL Queries
     */
    public DatabaseHandler(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.password = password;
        con = DriverManager.getConnection(this.url, this.username, this.password);
    }

   

    /**
     * Implement the autocloseable interface
     * @throws Exception 
     */
    @Override
    public void close() throws Exception {
        statement.close();
        con.close();
    }

    /**
     * set the url of the database connection
     * @param url url
     */
    @Override
    public void setUrl(String url) {
        this.url = url;
    }

    /**
     * set the username of the database connection
     * @param user username 
     */
    @Override
    public void setUser(String user) {
        this.username = user;
    }

    /**
     * set the password of the database connection
     * @param password password 
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * gets the conection
     * @return
     */
    @Override
    public Connection getConnection() {
        return this.con;
    }

    
    
    
    
    
}
