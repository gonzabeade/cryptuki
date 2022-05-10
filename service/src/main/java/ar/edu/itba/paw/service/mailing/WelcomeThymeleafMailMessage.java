package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class WelcomeThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "WelcomeTemplate";

    private String username;

    public WelcomeThymeleafMailMessage(String from, String to) {
        super(from, to, template);
    }
    public WelcomeThymeleafMailMessage(MailMessage mailMessage) {
        super(mailMessage, template);
    }


    public void setParameters(String username) {
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if ( username == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();
        context.setVariable("staticTitle", "¡Bienvenido!");
        context.setVariable("staticHello", "Hola");
        context.setVariable("staticGreeting", "¡bienvenido a Cryptuki!");
        context.setVariable("staticVerifyBeforeStarting", "Antes de empezar a comprar o vender crypto deberás verificar tu cuenta de email.");
        context.setVariable("staticVerifyWithLink", "Verifica tu cuenta de mail entrando al siguiente link");
        context.setVariable("staticVerifyAccount", "Verificar cuenta");
        context.setVariable("staticNeedMoreHelp", "¿Necesitas más ayuda?");
        context.setVariable("staticWriteUs", "Escríbenos");


        context.setVariable("username", username);
        return context;
    }
}
