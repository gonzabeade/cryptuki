package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class QuestionThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "QuestionTemplate";

    private String username;
    private String question;

    public QuestionThymeleafMailMessage(String from, String to) {
        super(from, to, template);
    }
    public QuestionThymeleafMailMessage(MailMessage mailMessage) {
        super(mailMessage, template);
    }


    public void setParameters(String username, String question) {
        this.question = question;
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if ( username == null || question == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();
        context.setVariable("staticTitle", "¡Consulta Recibida!");
        context.setVariable("staticHello", "Hola!");
        context.setVariable("staticQuestionReceived", "Hemos recibido tu consulta");
        context.setVariable("staticWillAnswer", "Esta será contestada a la brevedad por nuestro equipo.");
        context.setVariable("staticYourQuestion", "Tu consulta");
        context.setVariable("staticNeedMoreHelp", "¿Necesitas más ayuda?");
        context.setVariable("staticWriteUs", "Escríbenos");


        context.setVariable("question", question);
        context.setVariable("username", username);
        return context;
    }
}
