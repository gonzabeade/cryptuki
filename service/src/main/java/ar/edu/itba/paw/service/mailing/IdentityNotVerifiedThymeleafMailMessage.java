package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class IdentityNotVerifiedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "IdentityNotVerifiedTemplate";
    private String url;

    public IdentityNotVerifiedThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String url) {

        this.url = url;
    }

    @Override
    protected Context getContext() {

        Context context = new Context(getLocale());
        context.setVariable("url", url);

        return context;
    }
}
