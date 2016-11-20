
package qcas.questions.operations;

/**
 * QuestionMultipleAnswer a class dealing with fill in the blanks question
 *
 * @author Shraddha Patel
 */
public class QuestionFIB extends Question{
    private String answer;
    
    public QuestionFIB(String id, String type, String level, String description, String subjectCode,String answer) {
        super(id, type, level, description, subjectCode,new String[0]);
        this.answer=answer;        
    }

    public QuestionFIB(String type, String level, String description, String subjectCode,String answer) {
        super(type, level, description, subjectCode,new String[0]);
        this.answer=answer;
    }

    
    public String getAnswer() {
        return answer;
    }  
}
