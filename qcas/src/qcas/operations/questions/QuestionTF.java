
package qcas.operations.questions;

/**
 * QuestionTF a class dealing with true or false question
 *
 * @author Shraddha Patel
 */
public class QuestionTF extends Question{
    private boolean answer;
    
    /**
     * Blank constructor for some operations
     */
    public QuestionTF()
    {
       
    }
    
    public QuestionTF(String id, String type, String level, String description, String subjectCode,boolean answer) {
        super(id, type, level, description, subjectCode,new String[0]);
        this.answer=answer;        
    }

    public QuestionTF(String type, String level, String description, String subjectCode,boolean answer) {
        super(type, level, description, subjectCode,new String[0]);
        this.answer=answer;
    }

    
    public boolean getAnswer() {
        return answer;
    }
    
    public void setAnswer(boolean answer) {
        this.answer=answer;
    }
    
    
    @Override
    public String toString()
    {
       return super.toString()+" answer:"+ this.getAnswer();
    }
    
    
    @Override
    public boolean evaluate(Question question) {
        boolean check=true;
        if(question.getId().equals(this.getId())&&(question instanceof QuestionTF)){
            if(this.getAnswer()==((QuestionTF)question).getAnswer()){
                check=true;
            }
        }
        //System.out.print(check);
        return check;
    }
    
    
    @Override
    public Question getQuestion(String id,String questionType, String questionLevel, String questionDescription,String subjectCode,String choice1,String valid1,String choice2,String valid2,String choice3,String valid3,String choice4,String valid4) {
        Question question;
        boolean answer=false;
        if(choice1.equals("true"))
            answer=true;
        else
            answer=false;
        question = new QuestionTF(id,questionType,questionLevel, questionDescription, subjectCode,answer);
        return question;
    }
    
}
