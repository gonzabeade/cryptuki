package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class NewOfferThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NewOfferTemplate";

    private String username;
    private String coinCode;
    private double askingPrice;
    private double minQuantity;
    private double maxQuantity;
    private int offerId;

    public NewOfferThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String coinCode, double askingPrice, double minQuantity, double maxQuantity, int offerId) {
        this.username = username;
        this.coinCode = coinCode;
        this.askingPrice = askingPrice;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
        this.offerId = offerId;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();

        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("askingPrice", askingPrice);
        context.setVariable("minQuantity", minQuantity);
        context.setVariable("maxQuantity", maxQuantity);
        context.setVariable("offerId", offerId);
        return context;
    }
}
