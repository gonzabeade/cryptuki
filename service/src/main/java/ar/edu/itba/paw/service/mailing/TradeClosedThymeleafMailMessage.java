package ar.edu.itba.paw.service.mailing;

import org.springframework.context.i18n.LocaleContextHolder;
import org.thymeleaf.context.Context;

public class TradeClosedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "TradeClosed";

    private String username;
    private String buyer;
    private String quantity;
    private String coinCode;
    private String wallet;
    private String buyerEmail;
    private String tradeCode;

    public TradeClosedThymeleafMailMessage(String from, String to, ThymeleafMailHelper helper) {
        super(from, to, template, helper);
    }
    public TradeClosedThymeleafMailMessage(MailMessage mailMessage, ThymeleafMailHelper helper) {
        super(mailMessage, template, helper);
    }


    public void setParameters(String username, String coinCode, String quantity, String buyer, String wallet, String buyerMail, String tradeCode) {
        this.username = username;
        this.coinCode = coinCode;
        this.quantity = quantity;
        this.buyer = buyer;
        this.wallet = wallet;
        this.buyerEmail = buyerMail;
        this.tradeCode = tradeCode;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null || quantity == null || buyer == null || wallet == null || buyerEmail == null || tradeCode == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();


        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("quantity", quantity);
        context.setVariable("buyer", buyer);
        context.setVariable("wallet", wallet);
        context.setVariable("buyerEmail", buyerEmail);
        context.setVariable("tradeCode", tradeCode);
        context.setVariable("receiptLink", "http://locahost:8080/webapp/buy/" + tradeCode);

        return context;
    }
}
