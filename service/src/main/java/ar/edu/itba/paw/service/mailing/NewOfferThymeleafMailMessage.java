package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class NewOfferThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NewOfferTemplate";

    private String username;
    private String coinCode;
    private String askingPrice;
    private String minQuantity;
    private String maxQuantity;

    public NewOfferThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String coinCode, String askingPrice, String minQuantity, String maxQuantity) {
        this.username = username;
        this.coinCode = coinCode;
        this.askingPrice = askingPrice;
        this.minQuantity = minQuantity;
        this.maxQuantity = maxQuantity;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null || askingPrice == null || minQuantity == null || maxQuantity == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();

        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("askingPrice", askingPrice);
        context.setVariable("minQuantity", minQuantity);
        context.setVariable("maxQuantity", maxQuantity);
        return context;
    }
}
