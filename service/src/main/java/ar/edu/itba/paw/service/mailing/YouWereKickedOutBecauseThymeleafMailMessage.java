package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class YouWereKickedOutBecauseThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "YouWereKickedOutBecauseTemplate";

    private String username;
    private String coinCode;
    private double unitPrice;
    private String offerLocation;
    private String offerDate;
    private int offerId;

    private String url;

    public YouWereKickedOutBecauseThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String username, String coinCode, String offerLocation, String offerDate,  double unitPrice, int offerId, String url) {
        this.username = username;
        this.coinCode = coinCode;
        this.unitPrice = unitPrice;
        this.offerLocation =  offerLocation;
        this.offerDate = offerDate;
        this.offerId = offerId;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if ( username == null || coinCode == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());

        context.setVariable("username", username);
        context.setVariable("coinCode", coinCode);
        context.setVariable("unitPrice", unitPrice);
        context.setVariable("offerId", offerId);
        context.setVariable("offerDate", offerDate);
        context.setVariable("offerLocation", offerLocation);
        context.setVariable("url", url);

        return context;
    }
}
