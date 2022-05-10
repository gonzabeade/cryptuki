package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class WelcomeThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "WelcomeTemplate";

    private String username;
    private String verifyCode;

    public WelcomeThymeleafMailMessage(String from, String to, ThymeleafProcessor helper) {
        super(from, to, template, helper);
    }
    public WelcomeThymeleafMailMessage(MailMessage mailMessage, ThymeleafProcessor helper) {
        super(mailMessage, template, helper);
    }


    public void setParameters(String username, String verifyCode) {
        this.username = username;
        this.verifyCode = verifyCode;
    }

    @Override
    protected Context getContext() {

        if ( username == null || verifyCode == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();

        context.setVariable("username", username);
        context.setVariable("verifyLink", "http://localhost:8080/webapp/verify?user=" + username + "&code=" + verifyCode);
        return context;
    }
}
