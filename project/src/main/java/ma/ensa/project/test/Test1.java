package ma.ensa.project.test;

import com.itextpdf.styledxmlparser.jsoup.nodes.Document;
import ma.ensa.project.entity.Permission;
import ma.ensa.project.entity.PermissionEnum;
import ma.ensa.project.entity.Role;
import ma.ensa.project.entity.User;
import ma.ensa.project.service.MailService;
import ma.ensa.project.service.UserService;
//import org.apache.pdfbox.pdmodel.PDDocument;
//import org.apache.pdfbox.pdmodel.PDPage;
//import org.apache.pdfbox.pdmodel.PDPageContentStream;
//import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.Assertion;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Test1 {

    @Test
    public void mail() {
        //kawtar.zouiher.02@gmail.com
        //bilallaariny01@gmail.com
        MailService mailService = new MailService();
        boolean m = mailService.send("fahlaouimohammed@gmail.com", "test", "chouf lta7t", "C:\\Users\\pc\\Documents\\java2\\GestionFacturation\\project\\src\\main\\resources\\f.pdf");
        Assert.assertTrue(m);

    }
    @Test
    public void pdf() throws FileNotFoundException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {


//                 Add Invoice Header

                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 20);
                contentStream.newLineAtOffset(220, 750); // X, Y coordinates
                contentStream.showText("Facture");
                contentStream.endText();






                // Add Client Details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, 640);
                contentStream.showText("Client Name: ");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Address: 456 Elm Street");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Contact: +212 611 111 111");
                contentStream.endText();

                // Add Invoice Details
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(400, 700);
                contentStream.showText("Invoice #: 12345");
                contentStream.newLineAtOffset(0, -15);
                contentStream.showText("Date: 01-01-2025");
                contentStream.endText();

                // Add Table Header
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 580);
                contentStream.showText("Item");
                contentStream.newLineAtOffset(200, 0);
                contentStream.showText("Quantity");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Price");
                contentStream.newLineAtOffset(100, 0);
                contentStream.showText("Total");
                contentStream.endText();

                // Add Table Rows (Example Items)
                int yPosition = 560; // Start position
                String[][] items = {
                        {"Product A", "2", "50", "100"},
                        {"Product B", "1", "75", "75"},
                        {"Service C", "3", "30", "90"}
                };

                contentStream.setFont(PDType1Font.HELVETICA, 12);
                for (String[] item : items) {
                    contentStream.beginText();
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(item[0]);
                    contentStream.newLineAtOffset(200, 0);
                    contentStream.showText(item[1]);
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(item[2]);
                    contentStream.newLineAtOffset(100, 0);
                    contentStream.showText(item[3]);
                    contentStream.endText();
                    yPosition -= 20; // Move to the next row
                }

                // Add Total Amount
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
                contentStream.newLineAtOffset(350, yPosition - 20);
                contentStream.showText("Grand Total: 265");
                contentStream.endText();
            }

            // Save the PDF to a file
            document.save("facture.pdf");
            System.out.println("Invoice PDF created successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}


