package qcas.operations.questions;

import java.util.Arrays;

/**
 * QuestionMultipleAnswer a class dealing with multiple choice-multiple answer
 * question
 *
 * @author Shraddha Patel
 */
public class QuestionMultipleAnswer extends Question {

    private int[] answer;
    
    /**
     * Blank constructor for some operations
     */
    public QuestionMultipleAnswer()
    {
       
    }

    public QuestionMultipleAnswer(String id, String type, String level, String description, String subjectCode, int answer[], String... choices) {
        super(id, type, level, description, subjectCode, choices);

        this.answer = answer;
    }

    public QuestionMultipleAnswer(String type, String level, String description, String subjectCode, int answer[], String... choices) {
        super(type, level, description, subjectCode, choices);

        this.answer = answer;
    }

    public int[] getAnswer() {
        return answer;
    }

    @Override
    public String toString() {

        return super.toString() + " answer:" + Arrays.toString(this.getAnswer());
    }

    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
    
    
    @Override
    public boolean evaluate(Question question) {
        boolean check=true;
        if(question.getId().equals(this.getId())&&(question instanceof QuestionMultipleAnswer)){
        for(int i=0;i<this.getAnswer().length;i++){
            if(!(this.getAnswer()==((QuestionMultipleAnswer)question).getAnswer())){
                check = false;
            }
        }
        }else{
            check = false;
        }
        //System.out.print(check);
        return check;
    }
    
    @Override
    public Question getQuestion(String id,String questionType, String questionLevel, String questionDescription,String subjectCode,String choice1,String valid1,String choice2,String valid2,String choice3,String valid3,String choice4,String valid4) {
        Question question;
        int[] answer = new int[]{0,0,0,0};        
        if(valid1.equals("1"))
            answer[0]=1;
        if (valid2.equals("1"))
            answer[1]=1;
        if (valid3.equals("1"))
            answer[2]=1;
        if (valid4.equals("1"))
            answer[3]=1;
        question = new QuestionMultipleAnswer(id,questionType,questionLevel, questionDescription, subjectCode,answer,new String[]{choice1,choice2,choice3,choice4});
        return question;
    }
    
    
}
