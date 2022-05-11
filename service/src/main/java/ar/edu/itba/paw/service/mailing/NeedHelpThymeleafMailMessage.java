package ar.edu.itba.paw.service.mailing;

import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

public class NeedHelpThymeleafMailMessage extends ThymeleafMailMessage {

    private final static String template = "NeedHelpTemplate";

    private String username;
    private String question;
    private String answer;

    public NeedHelpThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String question, String answer) {
        this.answer = answer;
        this.question = question;
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if ( username == null || question == null || answer == null )
            throw new IllegalStateException("Cannot send email with missing parameters");
        Context context = new Context(getLocale());

        context.setVariable("question", question);
        context.setVariable("username", username);
        context.setVariable("answer", answer);
        return context;
    }
}
