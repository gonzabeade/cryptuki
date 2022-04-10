package ar.edu.itba.paw.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class MailService implements ContactService<MailMessage> {

    private JavaMailSender mailSender;
    private String mainSender;

    @Override
    public void sendMessage(MailMessage message) {
        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        // TODO: What goes instead of UTF-8?
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            messageHelper.setSubject(message.getBody());
            messageHelper.setFrom(message.getFrom());
            messageHelper.setTo(message.getTo());
            messageHelper.setText(message.getBody());
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public MailMessage createMessage(String from, String to) {
        return new MailMessage(from, to);
    }


    public MailService(String email, String password) {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        properties.put("mail.smtp.connectiontimeout", "t1");
        properties.put("mail.smtp.timeout", "t2");

        mailSender.setSession(
                Session.getInstance(properties, new Authenticator(){
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(email, password);
                    }
                })
        );

        this.mailSender = mailSender;
    }

}
