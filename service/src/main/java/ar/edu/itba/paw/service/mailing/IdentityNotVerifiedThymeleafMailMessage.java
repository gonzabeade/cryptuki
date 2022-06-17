package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class IdentityNotVerifiedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "IdentityNotVerifiedTemplate";

    private String username;
    private int code;
    private String url;

    public IdentityNotVerifiedThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, int code, String url) {
        this.username = username;
        this.code = code;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if (username == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());

        context.setVariable("username", username);
        context.setVariable("code", code);
        context.setVariable("url", url);

        return context;
    }
}
