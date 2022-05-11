package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class TradeOpenedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "TradeOpened";

    private String username;
    private float quantity;
    private String coinCode;
    private String buyer;
    private String buyerMessage;
    private String buyerMail;
    private String url;

    public TradeOpenedThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String coinCode, float quantity, String buyer, String buyerMessage, String buyerMail, String url) {
        this.username = username;
        this.coinCode = coinCode;
        this.quantity = quantity;
        this.buyer = buyer;
        this.buyerMessage = buyerMessage;
        this.buyerMail = buyerMail;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null || buyer == null || buyerMessage == null || buyerMail == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());

        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("quantity", quantity);
        context.setVariable("buyer", buyer);
        context.setVariable("buyerMessage", buyerMessage);
        context.setVariable("buyerMail", buyerMail);
        context.setVariable("url", url);

        return context;
    }
}
