package ar.edu.itba.paw.service.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Locale;

public class QuestionThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "QuestionTemplate";

    private String username;
    private String question;

    private String url;

    public QuestionThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String question, String url) {
        this.question = question;
        this.username = username;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if ( username == null || question == null || url == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());
        context.setVariable("question", question);
        context.setVariable("username", username);
        context.setVariable("url", url);

        return context;
    }
}
