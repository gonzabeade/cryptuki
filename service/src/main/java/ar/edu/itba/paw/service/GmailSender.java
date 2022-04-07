package ar.edu.itba.paw.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


@Service
public class GmailSender extends JavaMailSenderImpl{


    public GmailSender(){
        super();

        Properties properties = new Properties();

        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.connectiontimeout", "t1");
        properties.put("mail.smtp.timeout", "t2");

        String myAccountEmail = "TestPaw25@gmail.com";
        String password = "esteesuntestdepaw";

        setSession(
                Session.getInstance(properties, new Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(myAccountEmail, password);
                    }
                })
        );

    }

    public void send(String from, String to, String head, String body) throws MessagingException{
        final MimeMessage mimeMessage = super.createMimeMessage();
        final MimeMessageHelper message = new MimeMessageHelper(mimeMessage, "UTF-8"); //todo mirar que poner en vez de la codif UTF-8
        message.setSubject(head);
        message.setFrom(from);
        message.setTo(to);
        message.setText(body, false);
        super.send(mimeMessage);
    }



}
