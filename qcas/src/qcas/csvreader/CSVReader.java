package qcas.csvreader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import qcas.questions.operations.Question;
import qcas.questions.operations.QuestionFIB;
import qcas.questions.operations.QuestionMultipleAnswer;
import qcas.questions.operations.QuestionMultipleChoice;
import qcas.questions.operations.QuestionTF;

/**
 * CSVReader methods to load and read a quiz in the csv format
 *
 * @author Shraddha Patel
 */
public class CSVReader {

    private String filename = null;
    private ArrayList<Question> questions = new ArrayList<Question>();
    private String subjectCode;

    public CSVReader(String filename, String subjectCode) {
        this.filename = filename;
        this.subjectCode = subjectCode;
    }

    public boolean ParseCSV() {
        if (filename == null) {
            return false;
        }
        Reader in;
        try {
            in = new FileReader(filename);
            final CSVFormat csvFileFormat = CSVFormat.RFC4180.withIgnoreSurroundingSpaces().newFormat(',').withEscape('"');
            Iterable<CSVRecord> records = csvFileFormat.parse(in);
            for (CSVRecord record : records) {
                Question question = getQuestion(record, this.subjectCode);
                if (question != null) {
                    questions.add(question);
                }
            }
            if (questions.size() != 0) {
                return true;
            } else {
                return false;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        } catch (IOException ex) {
            Logger.getLogger(CSVReader.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public String getFilename() {
        return filename;
    }

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    private Question getQuestion(CSVRecord record, String subjectCode) {
        Question question;
        if (record.size() <= 0) {
            return null;
        }
        String type = record.get(0);
        switch (type) {
            case "MC":
            case "MA":
                if (record.size() != 11) {
                    return null;
                }
                break;
            case "TF":
            case "FIB":
                if (record.size() != 4) {
                    return null;
                }
                break;
            default:
                return null;
        }

        String level = record.get(1);
        String description = record.get(2);

        int i = 0;
        switch (type) {
            case "MC":
                int answer = -1;
                String[] choices = new String[4];
                for (i = 0; i < 4; i++) {
                    String choice = record.get(3 + (i * 2));
                    choices[i] = choice;

                    String correct = record.get(4 + (i * 2));
                    if (correct.equals("correct")) {
                        if (answer != -1) {
                            return null;
                        } else {
                            answer = i;
                        }
                    }

                }

                if (answer != -1) {

                    question = new QuestionMultipleChoice(type, level, description, subjectCode, answer, choices);

                } else {
                    return null;
                }
                break;
            case "MA":
                ArrayList<Integer> answerchoices = new ArrayList<Integer>();
                choices = new String[4];
                for (i = 0; i < 4; i++) {
                    String choice = record.get(3 + (i * 2));
                    choices[i] = choice;

                    String correct = record.get(4 + (i * 2));
                    if (correct.equals("correct")) {
                        answerchoices.add(i);
                    }
                }
                if (answerchoices.size() != 0) {
                    int[] answerchoicesArray = answerchoices.stream().mapToInt(m -> m).toArray();

                    question = new QuestionMultipleAnswer(type, level, description, subjectCode, answerchoicesArray, choices);
                } else {
                    return null;
                }
                break;
            case "TF":
                boolean choice;
                if (record.get(3).equals("true")) {
                    choice = true;
                } else if (record.get(4).equals("false")) {
                    choice = false;
                } else {
                    return null;
                }
                question = new QuestionTF(type, level, description, subjectCode, choice);
                break;
            case "FIB":
                if (!(record.get(3).length() > 0)) {
                    return null;
                }
                question = new QuestionFIB(type, level, description, subjectCode, record.get(3));
                break;
            default:
                return null;

        }
        return question;
    }

}
