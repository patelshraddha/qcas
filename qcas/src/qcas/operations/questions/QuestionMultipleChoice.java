  package qcas.operations.questions;

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
        super(type, level, description, subjectCode,choices);
        this.answer=answer;
    }

    
    public int getAnswer() {
        return answer;
    }
    
    @Override
    public String toString()
    {
       return super.toString()+" answer:"+ this.getChoices().get(answer);
    }

    public void setAnswer(int answer) {
       this.answer = answer;
    }

    @Override
    public boolean evaluate(Question question) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
