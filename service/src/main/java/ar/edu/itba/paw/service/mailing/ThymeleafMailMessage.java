package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public abstract class ThymeleafMailMessage extends MailMessage {

    private String template;

    private ThymeleafProcessor helper;

    public ThymeleafMailMessage(String from, String to, String template, ThymeleafProcessor helper) {
        super(from, to);
        super.setHtml(true);
        this.template = template;
        this.helper = helper;
    }

    public ThymeleafMailMessage(MailMessage mailMessage, String template, ThymeleafProcessor helper) {
        super(mailMessage);
        super.setHtml(true);
        this.template = template;
        this.helper = helper;
    }

    @Override
    public void setHtml(boolean isHtml) {
        throw new UnsupportedOperationException("Thymeleaf emails are always html.");
    }

    @Override
    public void setBody(String message) {
        throw new UnsupportedOperationException("Thymeleaf emails are always html.");
    }

    protected String getTemplate() {
        return this.template;
    }
    protected abstract Context getContext();

    public String getBody() {
        return helper.process(getTemplate(), getContext());
    }



}

