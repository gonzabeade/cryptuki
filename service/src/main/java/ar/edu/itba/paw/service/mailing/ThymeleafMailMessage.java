package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import java.nio.charset.StandardCharsets;

public abstract class ThymeleafMailMessage extends MailMessage {

    private TemplateEngine templateEngine;
    private ClassLoaderTemplateResolver templateResolver;

    private String template;

    private void configureClasses() {

        templateEngine = new TemplateEngine();
        templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);

        templateEngine.addTemplateResolver(templateResolver);
    }


    public ThymeleafMailMessage(String from, String to, String template) {
        super(from, to);
        configureClasses();
        this.template = template;
        super.setHtml(true);
    }

    public ThymeleafMailMessage(MailMessage mailMessage, String template) {
        super(mailMessage);
        configureClasses();
        this.template = template;
        super.setHtml(true);
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
        return templateEngine.process(getTemplate(), getContext());
    }



}

