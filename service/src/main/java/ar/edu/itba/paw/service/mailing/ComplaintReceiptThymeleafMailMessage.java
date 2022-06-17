package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.model.Trade;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class ComplaintReceiptThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "ComplaintReceiptTemplate";

    private Trade trade;
    private String complaint;
    private String url;


    public ComplaintReceiptThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(Trade trade, String complaint, String url) {
        this.complaint = complaint;
        this.url = url;
        this.trade = trade;
    }

    @Override
    protected Context getContext() {

        if ( trade == null || complaint == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());
        context.setVariable("complaint", complaint);
        context.setVariable("trade", trade);
        context.setVariable("url", url);

        return context;
    }
}
