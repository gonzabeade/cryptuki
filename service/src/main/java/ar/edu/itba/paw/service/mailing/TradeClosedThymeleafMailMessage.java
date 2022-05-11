package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class TradeClosedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "TradeClosed";

    private String username;
    private String buyer;
    private float quantity;
    private String coinCode;
    private String wallet;
    private int tradeCode;


    public TradeClosedThymeleafMailMessage(MailMessage mailMessage, TemplateEngine helper) {
        super(mailMessage, template, helper);
    }


    public void setParameters(String username, String coinCode, float quantity, String buyer, String wallet, int tradeCode) {
        this.username = username;
        this.coinCode = coinCode;
        this.quantity = quantity;
        this.buyer = buyer;
        this.wallet = wallet;
        this.tradeCode = tradeCode;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null || buyer == null || wallet == null )
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());


        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("quantity", quantity);
        context.setVariable("buyer", buyer);
        context.setVariable("wallet", wallet);
        context.setVariable("tradeCode", tradeCode);
        return context;
    }
}
