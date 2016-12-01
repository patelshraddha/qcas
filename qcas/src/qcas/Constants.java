/*
 */
package qcas;

/**
 * Contains all the global constants
 * @author Deepak
 */
public class Constants {
    
 //Database constants

    /**
     * Type of SQL Server. MySQL is used here.
     */
 public static final String DATABASEDRIVER="jdbc:mysql://";

    /**
     * Database URL
     */
    public static final String DATABASEURL="138.197.194.23:3306/qcas";

    /**
     *Database username
     */
    public static final String USERNAME="akshay";

    /**
     *Database password
     */
    public static final String USERPASSWORD="akshayakshay";
 
 
 //FXML paths

    /**
     *Fxml files location 
     */
 public static final String FXMLPATH ="views/";   

    /**
     *FXML login file location
     */
    public static final String LOGINSCREENFXML=FXMLPATH+"Login.fxml";

    /**
     *FXML professor dashboard file location
     */
    public static final String PROFESSORDASHBOARDFXML=FXMLPATH+"DashboardProfessor.fxml";

    /**
     *FXML student dashboard file location
     */
    public static final String STUDENTDASHBOARDFXML=FXMLPATH+"DashboardStudent.fxml";
 
 
 //identifiers for the profiles

 public static final String PROFESSORTYPE="p";

    public static final String STUDENTTYPE="s";
 
 //images and other paths

    /**
     * Path of image files
     */
 public static final String pathToAssets="views/assests/";

    public static final String clgLogo=pathToAssets+"2000px-Carnegie_Mellon_wordmark.svg.png";

    public static final String homeImg=pathToAssets+"home.jpg";

    public static final String reportImg=pathToAssets+"report.png";

    public static final String clipboardImg=pathToAssets+"clipboard.jpg";
    
    public static final String cmuIconImg=pathToAssets+"cmuicon.png";
    
 
 //timer

    /**
     * Timer per question
     */
 public static final int TIME_PER_QUESTION = 90;
 
    /**
     * Types of reports to be generated
     */
    public static final String[] REPORTTYPES = {"Number of Tests Taken.","Average Student Scores.","Scores by level of difficulty.","Students passing and failing."};

}
