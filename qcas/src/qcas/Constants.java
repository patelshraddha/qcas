/*
 */
package qcas;

/**
 *
 * @author Deepak
 */
public class Constants {
    
 //Database constants
 public static final String databaseDriver="jdbc:mysql://";
 public static final String databaseUrl="138.197.194.23:3306/qcas";
 public static final String userName="akshay";
 public static final String userPassword="akshayakshay";
 
 
 //FXML paths
 public static final String fxmlPath ="/qcas/views/";   
 public static final String loginScreenfxml=fxmlPath+"login.fxml";
 public static final String professorDashboardfxml=fxmlPath+"DashboardProfessor.fxml";
 public static final String studentDashboardfxml=fxmlPath+"DashboardStudent.fxml";
 
 
 //identifiers for the profiles
 public static final String professorType="p";
 public static final String studentType="s";
 
 //images and other paths
 public static final String pathToAssets="../assests/";
 public static final String clgLogo=pathToAssets+"2000px-Carnegie_Mellon_wordmark.svg.png";
 public static final String homeImg=pathToAssets+"home.jpg";
 public static final String reportImg=pathToAssets+"report.png";
 public static final String clipboardImg=pathToAssets+"clipboard.jpg";
}
