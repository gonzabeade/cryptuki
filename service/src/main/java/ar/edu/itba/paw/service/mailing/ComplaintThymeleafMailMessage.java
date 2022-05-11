package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ComplaintThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "ComplaintTemplate";

    private String username;
    private String complaint;

    public ComplaintThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String complaint) {
        this.complaint = complaint;
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if ( username == null || complaint == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());
        context.setVariable("complaint", complaint);
        context.setVariable("username", username);
        return context;
    }
}
