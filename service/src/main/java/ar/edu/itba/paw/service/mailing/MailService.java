package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.service.ContactService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class MailService implements ContactService<MailMessage> {

    private JavaMailSender mailSender;
    private String mainSender;

    @Override
    public void sendMessage(MailMessage message) {

        if (!message.getFrom().equals(mainSender))
            throw new IllegalArgumentException("Cannot send emails from other account than the one configured");

        final MimeMessage mimeMessage = mailSender.createMimeMessage();
        // TODO: What goes instead of UTF-8?
        final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, "UTF-8");
        try {
            messageHelper.setSubject(message.getSubject());
            messageHelper.setFrom(message.getFrom());
            messageHelper.setTo(message.getTo());
            messageHelper.setText(message.getBody(), message.isHtml());
        } catch (MessagingException e) {
            e.printStackTrace();//TODO: replace with logger.
        }
        mailSender.send(mimeMessage);
    }

    @Override
    public MailMessage createMessage(String to) {
        return new MailMessage(mainSender, to);
    }

    public MailService(String email, String password) {

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
                        return new PasswordAuthentication(email, password);
                    }
                })
        );

        this.mailSender = mailSender;
    }

}
