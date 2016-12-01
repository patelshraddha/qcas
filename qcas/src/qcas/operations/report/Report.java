/*
 */
package qcas.operations.report;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.Chart;
import javafx.scene.image.WritableImage;
import javax.imageio.ImageIO;
import qcas.operations.exam.Exam;
import qcas.operations.user.User;
import qcas.views.controllers.DashboardStudentController;

/**
 *
 * @author Deepak
 */
public class Report {
    
    public static void produceReport(Exam exam, User user,String filepath,Chart chart)
    {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        File file = new File(filepath);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
            com.itextpdf.text.Image graph;
            graph = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());
            int indentation = 0;
            float scaler = (float) (((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - indentation) / image.getWidth()) * 60);
            graph.scalePercent(scaler);
            graph.setAlignment(Image.MIDDLE);
            document.open();
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                       
            Paragraph name = new Paragraph("Name:  "+ user.getFirstName() + " " + user.getLastName(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
            String level = "";
            switch (exam.getDifficulty()) {
                case "E":
                    level = "Easy";
                    break;
                case "M":
                    level = "Medium";
                    break;
                case "H":
                    level = "Hard";
                    break;
                case "Mixed":
                    level = "Mixed";
                    break;
                default:
                    break;
            }
            Paragraph examdetails = new Paragraph("Exam date:  "+ dateFormat.format(exam.getExamDate()) + " \n" + "Difficulty:  "+level+" \n"+"Number of questions:  "+exam.getNumberOfQuestions(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
            
            
            String pass= exam.isPass() ? "Pass":"Fail";
            
            Paragraph splitter= new Paragraph("\n\n");
            Paragraph scores = new Paragraph("Pass/Fail: "+pass+"\nGrade:   "+ exam.getGrade() + "\n" + "Score: "+exam.getCorrectAnswers()+"/"+exam.getNumberOfQuestions(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
           
            
            document.add(name);
            document.add(examdetails);
            document.add(splitter);
            document.add(scores);
            document.add(graph);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void producePerformanceReport(User user,String subject,String report,Date date,String filepath,Chart chart)
    {
        WritableImage image = chart.snapshot(new SnapshotParameters(), null);
        File file = new File(filepath);

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(file));
            ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", byteOutput);
            com.itextpdf.text.Image graph;
            graph = com.itextpdf.text.Image.getInstance(byteOutput.toByteArray());
            int indentation = 0;
            float scaler = (float) (((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - indentation) / image.getWidth()) * 100);

            graph.scalePercent(scaler);
            document.open();
            
             SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                       
            Paragraph name = new Paragraph("Name:  "+ user.getFirstName() + " " + user.getLastName(),
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
            String level = "";
            Paragraph subjectdetails = new Paragraph("Subject:  "+ subject ,
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
            
            Paragraph datedetails = new Paragraph("Date:  "+ dateFormat.format(date) ,
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
            Paragraph reportdetails = new Paragraph("Report:  "+ report ,
                    FontFactory.getFont(FontFactory.HELVETICA, 12, Font.NORMAL, new BaseColor(0, 0, 0)));
            
           
            
            Paragraph splitter= new Paragraph("\n\n");
            
            
            document.add(name);
            document.add(datedetails);
            document.add(subjectdetails);
            document.add(splitter);
            document.add(reportdetails);
            document.add(graph);
            document.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (DocumentException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(DashboardStudentController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
