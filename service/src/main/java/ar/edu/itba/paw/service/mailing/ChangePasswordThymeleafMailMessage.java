package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ChangePasswordThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NeedHelpTemplate";

    private String username;

    public ChangePasswordThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username) {
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if (username == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());

        context.setVariable("username", username);
        return context;
    }
}
