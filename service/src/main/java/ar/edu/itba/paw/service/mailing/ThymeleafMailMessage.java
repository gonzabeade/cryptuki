package ar.edu.itba.paw.service.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

public abstract class ThymeleafMailMessage extends MailMessage {

    private String template;

    private ThymeleafMailHelper helper;

    public ThymeleafMailMessage(String from, String to, String template, ThymeleafMailHelper helper) {
        super(from, to);
        super.setHtml(true);
        this.template = template;
        this.helper = helper;
    }

    public ThymeleafMailMessage(MailMessage mailMessage, String template, ThymeleafMailHelper helper) {
        super(mailMessage);
        super.setHtml(true);
        this.template = template;
        this.helper = helper;
    }

    @Override
    public void setHtml(boolean isHtml) {
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

