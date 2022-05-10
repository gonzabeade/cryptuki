package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class ChangePasswordThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NeedHelpTemplate";

    private String username;

    public ChangePasswordThymeleafMailMessage(String from, String to) {
        super(from, to, template);
    }
    public ChangePasswordThymeleafMailMessage(MailMessage mailMessage) {
        super(mailMessage, template);
    }


    public void setParameters(String username) {
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if (username == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();
        context.setVariable("staticTitle", "¡Nueva Contraseña");
        context.setVariable("staticHello", "Hola!");
        context.setVariable("staticYouForgotYourPassword", "Parece que olvidaste tu contraseña.");
        context.setVariable("staticChangePasswordWithLink", "Podrás cambiarla ingresando al siguiente link");
        context.setVariable("staticChangePassword", "Cambiar Contraseña");
        context.setVariable("staticNeedMoreHelp", "¿Necesitas más ayuda?");
        context.setVariable("staticWriteUs", "Escríbenos");


        context.setVariable("username", username);
        return context;
    }
}
