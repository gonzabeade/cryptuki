package ar.edu.itba.paw.service.mailing;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Component
public class ThymeleafProcessor {

    private TemplateEngine templateEngine;

    @Autowired
    public ThymeleafProcessor(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    public String process(String template, Context context) {
        String returning =  templateEngine.process(template, context);
        return returning;
    }

}
