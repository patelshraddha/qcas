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
}
