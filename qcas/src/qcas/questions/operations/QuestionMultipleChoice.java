package qcas.questions.operations;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * QuestionMultipleChoice a class dealing with multiple choice-single answer question
 *
 * @author Shraddha Patel
 */
public class QuestionMultipleChoice extends Question{
    
    private int answer;
    
    public QuestionMultipleChoice(String id, String type, String level, String description, String subjectCode,int answer,String... choices) {
        super(id, type, level, description, subjectCode,choices);
        this.answer=answer;        
    }

    public QuestionMultipleChoice(String type, String level, String description, String subjectCode,int answer,String... choices) {
        super(type, level, description, subjectCode);
        this.answer=answer;
    }

    
    public int getAnswer() {
        return answer;
    }
    
    
    
    
}