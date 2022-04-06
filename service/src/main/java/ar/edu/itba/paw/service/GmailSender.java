package ar.edu.itba.paw.service;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import javax.mail.*;
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

}
