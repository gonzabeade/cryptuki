package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

public class NewUnseenMessagesThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NewUnseenMessagesTemplate";

    private int tradeId;
    private String url;
    private boolean isSeller;

    public NewUnseenMessagesThymeleafMailMessage(MailMessage mailMessage, TemplateEngine templateEngine) {
        super(mailMessage, template, templateEngine);
    }


    public void setParameters(String url, int tradeId, boolean b) {
        this.tradeId = tradeId;
        this.url = url;
        this.isSeller= b;
    }

    @Override
    protected Context getContext() {

        Context context = new Context(getLocale());
        context.setVariable("tradeId", tradeId);
        if(isSeller){
            url += "/chat?tradeId=";
        }else{
            url+= "/trade?tradeId=";
        }
        context.setVariable("url", url);

        return context;
    }
}
