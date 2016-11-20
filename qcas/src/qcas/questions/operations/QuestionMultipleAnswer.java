
package qcas.questions.operations;

/**
 * QuestionMultipleAnswer a class dealing with multiple choice-multiple answer question
 *
 * @author Shraddha Patel
 */
public class QuestionMultipleAnswer extends Question{
    private int[] answer;
    
    public QuestionMultipleAnswer(String id, String type, String level, String description, String subjectCode,int answer[],String... choices) {
        super(id, type, level, description, subjectCode,choices);
        this.answer=answer;        
    }

    public QuestionMultipleAnswer(String type, String level, String description, String subjectCode,int answer[],String... choices) {
        super(type, level, description, subjectCode);
        this.answer=answer;
    }

    
    public int[] getAnswer() {
        return answer;
    }  
}
