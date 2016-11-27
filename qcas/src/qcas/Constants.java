/*
 */
package qcas;

/**
 *
 * @author Deepak
 */
public class Constants {
    
 //Database constants
 public static final String DATABASEDRIVER="jdbc:mysql://";
 public static final String DATABASEURL="138.197.194.23:3306/qcas";
 public static final String USERNAME="akshay";
 public static final String USERPASSWORD="akshayakshay";
 
 
 //FXML paths
 public static final String FXMLPATH ="views/";   
 public static final String LOGINSCREENFXML=FXMLPATH+"Login.fxml";
 public static final String PROFESSORDASHBOARDFXML=FXMLPATH+"DashboardProfessor.fxml";
 public static final String STUDENTDASHBOARDFXML=FXMLPATH+"DashboardStudent.fxml";
 
 
 //identifiers for the profiles
 public static final String PROFESSORTYPE="p";
 public static final String STUDENTTYPE="s";
 
 //images and other paths
 public static final String pathToAssets="views/assests/";
 public static final String clgLogo=pathToAssets+"2000px-Carnegie_Mellon_wordmark.svg.png";
 public static final String homeImg=pathToAssets+"home.jpg";
 public static final String reportImg=pathToAssets+"report.png";
 public static final String clipboardImg=pathToAssets+"clipboard.jpg";
}
