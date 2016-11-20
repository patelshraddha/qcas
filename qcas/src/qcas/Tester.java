/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package qcas;

import qcas.csvreader.CSVReader;

/**
 *
 * @author Deepak
 */
public class Tester {
   public  static void main(String[] args)
   {
       CSVReader reader = new CSVReader("test.csv");
       if(reader.ParseCSV())
       {
           System.out.println("Done");
       }
       else
       {
           System.out.println("sjssjsj");
       }
       
   }
}
