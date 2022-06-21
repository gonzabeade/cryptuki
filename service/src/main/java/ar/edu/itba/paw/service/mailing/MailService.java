package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.service.ContactService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailService implements ContactService<MailMessage> {

    private JavaMailSender mailSender;
    private String mainSender;
    private final PasswordAuthentication passwordAuthentication;
    private static final Logger LOGGER = LoggerFactory.getLogger(MailService.class);


    @Override
    @Async
    public void sendMessage(MailMessage message) {

        if (!message.getFrom().equals(mainSender))
            throw new IllegalArgumentException("Cannot send emails from other account than the one configured");

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            messageHelper.setSubject(message.getSubject());
            messageHelper.setFrom(message.getFrom());
            messageHelper.setTo(message.getTo());
            messageHelper.setText(message.getBody(), message.isHtml());
        } catch (MessagingException e) {
            LOGGER.error("Messaging exception when sending email: "+e.getMessage());
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public MailMessage createMessage(String to) {
        return new MailMessage(mainSender, to);
    }

    @Autowired
    public MailService(@Value("${mail.username}") String email, PasswordAuthentication passwordAuthentication) {
        this.passwordAuthentication = passwordAuthentication;

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mainSender = email;

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
                        return MailService.this.passwordAuthentication;
                    }
                })
        );
        this.mailSender = mailSender;
    }

}
