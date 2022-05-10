package ar.edu.itba.paw.service.config;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.mailing.MailMessage;
import ar.edu.itba.paw.service.mailing.MailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
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
    @Value("classpath:info")
    private Resource info;

    @Bean
    public ContactService<MailMessage> contactService() throws IOException {
        Reader reader = new InputStreamReader(info.getInputStream());
        JSONObject jsonObject = new JSONObject(FileCopyUtils.copyToString(reader));
        return new MailService(jsonObject.getString("mailUsername"), jsonObject.getString("mailPassword"));
    }

    @Bean
    public TemplateEngine templateEngine(MessageSource messageSource) {
        SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();

        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode("XHTML");
        templateResolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        templateResolver.setCacheable(false);
        templateEngine.setTemplateEngineMessageSource(messageSource);


        templateEngine.addTemplateResolver(templateResolver);
        return templateEngine;
    }


}