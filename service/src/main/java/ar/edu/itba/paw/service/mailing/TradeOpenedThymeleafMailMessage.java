package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class TradeOpenedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "TradeOpened";

    private String username;
    private String quantity;
    private String coinCode;
    private String buyer;
    private String buyerMessage;
    private String buyerMail;

    public TradeOpenedThymeleafMailMessage(String from, String to) {
        super(from, to, template);
    }
    public TradeOpenedThymeleafMailMessage(MailMessage mailMessage) {
        super(mailMessage, template);
    }


    public void setParameters(String username, String coinCode, String quantity, String buyer, String buyerMessage, String buyerMail) {
        this.username = username;
        this.coinCode = coinCode;
        this.quantity = quantity;
        this.buyer = buyer;
        this.buyerMessage = buyerMessage;
        this.buyerMail = buyerMail;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null || quantity == null || buyer == null || buyerMessage == null || buyerMail == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();

        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("quantity", quantity);
        context.setVariable("buyer", buyer);
        context.setVariable("buyerMessage", buyerMessage);
        context.setVariable("buyerMail", buyerMail);
        return context;
    }
}
