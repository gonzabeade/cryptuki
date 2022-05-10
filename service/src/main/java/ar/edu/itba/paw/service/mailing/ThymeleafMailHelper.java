package ar.edu.itba.paw.service.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ThymeleafMailHelper {

    private TemplateEngine templateEngine;

    @Autowired
    public ThymeleafMailHelper(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String process(String template, Context context) {
        String returning =  templateEngine.process(template, context);
        return returning;
    }

}
