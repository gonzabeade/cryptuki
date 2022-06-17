package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class AnonymousComplaintReceiptThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "AnonymousComplaintReceiptTemplate";

    private String question;
    private String url;

    public AnonymousComplaintReceiptThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters( String question, String url) {
        this.question = question;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if (question == null || url == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());
        context.setVariable("question", question);
        context.setVariable("url", url);

        return context;
    }
}
