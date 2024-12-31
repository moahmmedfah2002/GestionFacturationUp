package ma.ensa.project.service;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class MailService {
    public boolean send(String to, String subject, String m,String file) {
        String host="fahlaouimohammed@gmail.com";  //← my email address
        final String user="fahlaouimohammed@gmail.com";//← my email address
        final String password="vmxq wovh gapu bliz ";//change accordingly   //← my email password



        // session object
        Properties props = new Properties();
        props.put("mail.smtp.ssl.trust", "*");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {
                    protected javax.mail.PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(user,password);
                    }
                });

        //My message :
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
            message.setSubject(subject);



            // Set text message part


    // Part two is attachment
            Multipart multipart = new MimeMultipart();
            BodyPart messageBodyPart = new MimeBodyPart();
            DataSource source = new FileDataSource(file);
            message.setDataHandler(new DataHandler(source));
            messageBodyPart.setDataHandler(new DataHandler(source));
            messageBodyPart.setFileName("file.pdf");
            multipart.addBodyPart(messageBodyPart);
            BodyPart messageBodyPart2;
            messageBodyPart2 = new MimeBodyPart();
            messageBodyPart2.setContent(m,"text/html");
            multipart.addBodyPart(messageBodyPart2);

            message.setContent(multipart);

            message.saveChanges();



            // Send the complete message parts





            Transport.send(message);

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }

    }
}
