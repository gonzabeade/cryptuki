package ar.edu.itba.paw.service.config;

import ar.edu.itba.paw.service.ContactService;
import ar.edu.itba.paw.service.MailMessage;
import ar.edu.itba.paw.service.MailService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.util.FileCopyUtils;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Arrays;


@ComponentScan({
        "ar.edu.itba.paw.persistence",
        "ar.edu.itba.paw.service",
})
@Configuration
public class ServiceConfig {
    @Value("classpath:info")
    private Resource info;

    @Bean
    public ContactService<MailMessage> contactService() throws IOException {
        Reader reader = new InputStreamReader(info.getInputStream());
        JSONObject jsonObject = new JSONObject(FileCopyUtils.copyToString(reader));
        return new MailService(jsonObject.getString("mailUsername"), jsonObject.getString("mailPassword"));
    }


}