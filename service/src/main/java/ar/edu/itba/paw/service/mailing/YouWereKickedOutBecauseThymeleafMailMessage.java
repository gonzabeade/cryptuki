package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class YouWereKickedOutBecauseThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "YouWereKickedOutBecauseTemplate";

    private String reason;
    private String url;

    public YouWereKickedOutBecauseThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String url, String reason) {
        this.reason = reason;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        Context context = new Context(getLocale());
        context.setVariable("url", url);
        context.setVariable("reason", reason);

        return context;
    }
}
