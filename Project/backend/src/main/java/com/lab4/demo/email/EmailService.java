package com.lab4.demo.email;

import com.lab4.demo.student.model.Student;
import com.lab4.demo.student.model.dto.StudentDTO;
import lombok.AllArgsConstructor;
import org.apache.pdfbox.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeUtility;
import java.io.File;
import java.io.InputStream;

@Service
public class EmailService
{
    @Autowired
    private JavaMailSender mailSender;

    public void sendMail(String to, String subject, String body)
    {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("a.m.morosan30@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
    }


    public void sendMailWithAttachment(StudentDTO student)
    {
        MimeMessagePreparator preparator = new MimeMessagePreparator()
        {
            public void prepare(MimeMessage mimeMessage) throws Exception
            {
                mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(student.getEmailAddress()));
                mimeMessage.setFrom(new InternetAddress("a.m.morosan30@gmail.com"));
                mimeMessage.setSubject("Grades");
                String text =" The list of grades is attached!";
                mimeMessage.setText(text);

                FileSystemResource file = new FileSystemResource(new File("backend\\src\\main\\resources\\ClassReport"+student.getId()+".pdf"));
                System.out.println(file.contentLength());
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.addAttachment("ReportClasses.pdf", file, "application/pdf");
                helper.setText("Hello,\n" +
                        "The list of grades is attached");
            }
        };

        try {
            mailSender.send(preparator);
        }
        catch (MailException ex) {
            System.err.println(ex.getMessage());
        }
    }
}