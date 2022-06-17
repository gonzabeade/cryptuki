package ar.edu.itba.paw.service.mailing;

import ar.edu.itba.paw.model.Offer;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class OfferUploadedThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "OfferUploadedMessageTemplate";

    private Offer offer;

    private String url;

    public OfferUploadedThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(Offer offer,  String url) {
        this.offer = offer;
        this.url = url;
    }

    @Override
    protected Context getContext() {

        if ( offer == null )
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context(getLocale());
        context.setVariable("offer", offer);
        context.setVariable("url", url);

        return context;
    }
}
