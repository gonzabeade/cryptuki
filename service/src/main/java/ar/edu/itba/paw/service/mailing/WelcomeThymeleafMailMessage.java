package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class WelcomeThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "WelcomeTemplate";

    private String username;
    private int verifyCode;
    private String url;

    public WelcomeThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, int verifyCode, String url) {
        this.username = username;
        this.verifyCode = verifyCode;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if ( username == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());

        context.setVariable("username", username);
        context.setVariable("code", verifyCode);
        context.setVariable("url", url);
        return context;
    }
}
