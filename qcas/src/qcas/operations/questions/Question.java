package qcas.operations.questions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Question a generic class to process all question types
 *
 * @author Shraddha Patel
 */
public class  Question {

    private String id;
    private String type;
    private String level;
    private String description;
    private String subjectCode;
    private ArrayList<String> choices = new ArrayList<String>();

    /**
     * Constructor
     */
    public Question()
    {
        
    }
    
    /**
     *Constructor for questions 
     * @param id
     * @param type
     * @param level
     * @param description
     * @param subjectCode
     * @param choices
     */
    public Question(String id, String type, String level, String description, String subjectCode, String[] choices) {

        this(type, level, description, subjectCode, choices);
        this.id = id;
    }

    /**
     *Constructor for questions 
     * @param type
     * @param level
     * @param description
     * @param subjectCode
     * @param choices
     */
    public Question(String type, String level, String description, String subjectCode, String... choices) {
        this.type = type;
        this.level = level;
        this.description = description;
        this.subjectCode = subjectCode;
        this.choices = new ArrayList<String>();
        for(String choice: choices)
            this.choices.add(choice);
    }

    /**
     *gest choices
     * @return
     */
    public ArrayList<String> getChoices() {
        return choices;
    }

    /**
     *get the ids
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *gets the type
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *gets the level
     * @return
     */
    public String getLevel() {
        return level;
    }

    /**
     *gets the dsscription of the questions
     * @return
     */
    public String getDescription() {
        return description;
    }

    /**
     *gets the subject code of the questions
     * @return
     */
    public String getSubjectCode() {
        return subjectCode;
    }
    
    @Override
    public String toString()
    {
        return "Type:"+this.type+" Level:"+this.level+" Description:"+this.description+" SubjectCode:"+this.subjectCode+" Answer Choices:"+Arrays.toString(this.choices.toArray());
    }
    
    /**
     *evaluates questions
     * @param question
     * @return
     */
    public boolean evaluate(Question question)
    {
        return false;
    }

    /**
     *gets questions according to the requirements like question level subject code etc.
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
    public Question getQuestion(String id,String questionType, String questionLevel, String questionDescription,String subjectCode,String choice1,String valid1,String choice2,String valid2,String choice3,String valid3,String choice4,String valid4) {
        return null;
    }
    
    @Override
    public Question clone()
    {
        return new Question(this.getId(),this.getType(),this.getLevel(),this.getDescription(),this.getSubjectCode());
    }

    

}
