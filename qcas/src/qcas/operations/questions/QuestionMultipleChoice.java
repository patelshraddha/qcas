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
    
    /**
     * Blank constructor for some operations
     */
    public QuestionMultipleChoice()
    {
       
    }
    
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
        boolean check=false;
        if(question.getId().equals(this.getId())&&(question instanceof QuestionMultipleChoice)){
            if(this.getAnswer()==((QuestionMultipleChoice)question).getAnswer()){
                check=true;
            }
        }
        //System.out.print(check);
        return check;
    }
    
    @Override
    public Question getQuestion(String id,String questionType, String questionLevel, String questionDescription,String subjectCode,String choice1,String valid1,String choice2,String valid2,String choice3,String valid3,String choice4,String valid4) {
        Question question;
        int answer =0;
        if(valid1.equals("1"))
            answer=0;
        else if (valid2.equals("1"))
            answer=1;
        else if(valid3.equals("1"))
            answer=2;
        else
            answer=3;
        question = new QuestionMultipleChoice(id,questionType,questionLevel, questionDescription, subjectCode, answer,new String[]{choice1,choice2,choice3,choice4});
        return question;
    }
    
    
    @Override
    public Question clone()
    {
        return new QuestionMultipleChoice(this.getId(),this.getType(),this.getLevel(),this.getDescription(),this.getSubjectCode(),-1,this.getChoices().get(0),this.getChoices().get(1),this.getChoices().get(2),this.getChoices().get(3));
    }
    
}
