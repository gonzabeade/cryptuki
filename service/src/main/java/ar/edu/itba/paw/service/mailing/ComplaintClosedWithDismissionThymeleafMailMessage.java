package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ComplaintClosedWithDismissionThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "ComplaintClosedWithDismissionTemplate";

    private String commentsOnClose;
    private String url;


    public ComplaintClosedWithDismissionThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String commentsOnClose, String url) {
        this.commentsOnClose = commentsOnClose;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        Context context = new Context(getLocale());
        context.setVariable("complaint", commentsOnClose);
        context.setVariable("url", url);

        return context;
    }
}
