package ar.edu.itba.paw.service.config;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.mailing.MailMessage;
import ar.edu.itba.paw.service.mailing.MailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;


@ComponentScan({
        "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.service",
})
@Configuration
@EnableAsync
public class ServiceConfig {

    @Bean
    public ContactService<MailMessage> contactService(
            @Value("${mail.username}") String mail,
            @Value("${mail.password}") String password
    ) {
        return new MailService(mail, password);
    }

    @Bean
    public TemplateEngine templateEngine() {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        templateEngine.setTemplateEngineMessageSource(mailingMessageSource());
        templateEngine.addTemplateResolver(templateResolver);
        return templateEngine;
    }

    @Bean
    @Qualifier("mailingMessageSource")
    public MessageSource mailingMessageSource() {
        final ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("/i18n/mailing/messages");
        messageSource.setDefaultEncoding(StandardCharsets.ISO_8859_1.displayName());
        return messageSource;
    }


}