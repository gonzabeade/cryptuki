package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class NewUnseenMessagesThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NewUnseenMessagesTemplate";

    private int tradeId;
    private String url;

    public NewUnseenMessagesThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String url, int tradeId) {
        this.tradeId = tradeId;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        Context context = new Context(getLocale());
        context.setVariable("tradeId", tradeId);
        context.setVariable("url", url);

        return context;
    }
}
