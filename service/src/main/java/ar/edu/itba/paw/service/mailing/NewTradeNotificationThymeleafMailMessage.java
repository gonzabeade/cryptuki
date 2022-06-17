package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.model.Trade;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class NewTradeNotificationThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NewTradeNotificationTemplate";

    private Trade trade;
    private String url;

    public NewTradeNotificationThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(Trade trade, String url) {
        this.url = url;
        this.trade = trade;
    }

    @Override
    protected Context getContext() {

        if (trade == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());

        context.setVariable("trade", trade);
        context.setVariable("url", url);

        return context;
    }
}
