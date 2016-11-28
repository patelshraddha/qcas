
package qcas.operations.questions;

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
    
    @Override
    public String toString()
    {
       return super.toString()+" answer:"+ this.getAnswer();
    }
    
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    
    @Override
    public boolean evaluate(Question question) {
        boolean check=false;
        if(question.getId().equals(this.getId())&&(question instanceof QuestionFIB)){
            check = this.getAnswer().equals(((QuestionFIB)question).getAnswer());
        }
        //System.out.print(check);
        return check;
    }
    
    @Override
    public Question getQuestion(String questionType, String questionLevel, String questionDescription,String subjectCode,String choice1,String valid1,String choice2,String valid2,String choice3,String valid3,String choice4,String valid4) {
        Question question;
        question = new QuestionFIB(questionType,questionLevel, questionDescription, subjectCode,choice1);
        return question;
    }
    
    
    
}
