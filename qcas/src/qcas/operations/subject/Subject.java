/*
 */
package qcas.operations.subject;

/**
 * Stores the traits of a subject
 * @author Shraddha Patel
 */
public class Subject {
   private String subjectCode;
   private String subjectName;
   
    /**
     * Constructor
     * @param subjectCode
     * @param subjectName
     */
    public Subject(String subjectCode, String subjectName)
   {
       this.subjectCode= subjectCode;
       this.subjectName = subjectName;
   }

    /**
     * gets subject code
     * @return subject code
     */
    public String getSubjectCode() {
        return subjectCode;
    }

    /**
     * gets subject name
     * @return subject name
     */
    public String getSubjectName() {
        return subjectName;
    }
   
}
