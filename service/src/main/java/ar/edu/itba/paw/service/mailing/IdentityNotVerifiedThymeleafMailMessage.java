package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class IdentityNotVerifiedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "IdentityNotVerifiedTemplate";
    private String url;
    private String reason;

    public IdentityNotVerifiedThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String url, String reason) {
        this.url = url;
        this.reason = reason;
    }

    @Override
    protected Context getContext() {

        Context context = new Context(getLocale());
        context.setVariable("url", url);
        context.setVariable("reason", reason);

        return context;
    }
}
