package ar.edu.itba.paw.service.config;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.MailMessage;
import ar.edu.itba.paw.service.MailService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@ComponentScan({
        "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.service",
})
@Configuration
public class ServiceConfig {

    @Bean
    public ContactService<MailMessage> contactService() {
          return new MailService(System.getenv("MAIL_ADDRESS"), System.getenv("MAIL_PASS") );
    }

}