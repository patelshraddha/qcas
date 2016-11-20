/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import java.util.ArrayList;
import qcas.csvreader.CSVReader;
import qcas.questions.operations.Question;

/**
 *
 * @author Deepak
 */
public class Tester {
   public  static void main(String[] args)
   {
       CSVReader reader = new CSVReader("test.csv","OOP");
       if(reader.ParseCSV())
       {
           ArrayList<Question> questions = reader.getQuestions();
           for(Question question:questions)
               System.out.println(question);
       }
       else
       {
           System.out.println("sjssjsj");
       }
       
   }
}
