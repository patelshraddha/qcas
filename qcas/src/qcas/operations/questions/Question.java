package qcas.operations.questions;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Question a generic class to process all question types
 *
 * @author Shraddha Patel
 */
public class Question {

    private String id;
    private String type;
    private String level;
    private String description;
    private String subjectCode;
    private ArrayList<String> choices = new ArrayList<String>();

    public Question(String id, String type, String level, String description, String subjectCode, String[] choices) {

        this(type, level, description, subjectCode, choices);
        this.id = id;
    }

    public Question(String type, String level, String description, String subjectCode, String... choices) {
        this.type = type;
        this.level = level;
        this.description = description;
        this.subjectCode = subjectCode;
        this.choices = new ArrayList<String>();
        for(String choice: choices)
            this.choices.add(choice);
    }

    public ArrayList<String> getChoices() {
        return choices;
    }

    public String getId() {
        return id;
    }

    public String getType() {
        return type;
    }

    public String getLevel() {
        return level;
    }

    public String getDescription() {
        return description;
    }

    public String getSubjectCode() {
        return subjectCode;
    }
    
    @Override
    public String toString()
    {
        return "Type:"+this.type+" Level:"+this.level+" Description:"+this.description+" SubjectCode:"+this.subjectCode+" Answer Choices:"+Arrays.toString(this.choices.toArray());
    }

}
