/*
 */
package qcas.operations.subject;

/**
 *
 * @author Shraddha Patel
 */
public class Subject {
   private String subjectCode;
   private String subjectName;
   
   public Subject(String subjectCode, String subjectName)
   {
       this.subjectCode= subjectCode;
       this.subjectName = subjectName;
   }

    public String getSubjectCode() {
        return subjectCode;
    }

    public String getSubjectName() {
        return subjectName;
    }
   
}
