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

    /**
     *gets multiple choice answers
     * @param id
     * @param type
     * @param level
     * @param description
     * @param subjectCode
     * @param answer
     * @param choices
     */
    public QuestionMultipleAnswer(String id, String type, String level, String description, String subjectCode, int answer[], String... choices) {
        super(id, type, level, description, subjectCode, choices);

        this.answer = answer;
    }

    /**
     *gets multiple choice answers
     * @param type
     * @param level
     * @param description
     * @param subjectCode
     * @param answer
     * @param choices
     */
    public QuestionMultipleAnswer(String type, String level, String description, String subjectCode, int answer[], String... choices) {
        super(type, level, description, subjectCode, choices);

        this.answer = answer;
    }

    /**
     *Gets answer of multiple choice questions
     * @return
     */
    public int[] getAnswer() {
        return answer;
    }

    @Override
    public String toString() {

        return super.toString() + " answer:" + Arrays.toString(this.getAnswer());
    }

    /**
     *Gets answer of multiple choice questions
     * @param answer
     */
    public void setAnswer(int[] answer) {
        this.answer = answer;
    }
    
    /**
     *evaluates answer of multiple choice questions
     * @param question
     * @return
     */
    @Override
    public boolean evaluate(Question question) {
        boolean check=true;
        if(question.getId().equals(this.getId())&&(question instanceof QuestionMultipleAnswer)){
        for(int i=0;i<this.getAnswer().length;i++){
            if(!(this.getAnswer()[i]==((QuestionMultipleAnswer)question).getAnswer()[i])){
                check = false;
            }
        }
        }else{
            check = false;
        }
        //System.out.print(check);
        return check;
    }
    
    /**
     *Gets questions of multiple choice questions
     * @param id
     * @param questionType
     * @param questionLevel
     * @param questionDescription
     * @param subjectCode
     * @param choice1
     * @param valid1
     * @param choice2
     * @param valid2
     * @param choice3
     * @param valid3
     * @param choice4
     * @param valid4
     * @return
     */
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
    
    
    @Override
    public Question clone()
    {
        return new QuestionMultipleAnswer(this.getId(),this.getType(),this.getLevel(),this.getDescription(),this.getSubjectCode(),new int[]{0,0,0,0},this.getChoices().get(0),this.getChoices().get(1),this.getChoices().get(2),this.getChoices().get(3));
    }
    
}
