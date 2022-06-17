package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ComplaintClosedWithKickoutThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "ComplaintClosedWithKickoutTemplate";

    private String username;
    private String complaint;
    private String url;


    public ComplaintClosedWithKickoutThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String complaint, String url) {
        this.complaint = complaint;
        this.username = username;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if ( username == null || complaint == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());
        context.setVariable("complaint", complaint);
        context.setVariable("username", username);
        context.setVariable("url", url);

        return context;
    }
}
