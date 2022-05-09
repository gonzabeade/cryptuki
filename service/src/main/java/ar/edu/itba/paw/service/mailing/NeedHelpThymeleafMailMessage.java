package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class NeedHelpThymeleafMailMessage extends ThymeleafMailMessage {

    private final static String template = "NeedHelpTemplate";

    private String username;
    private String question;
    private String answer;

    public NeedHelpThymeleafMailMessage(String from, String to) {
        super(from, to, template);
    }
    public NeedHelpThymeleafMailMessage(MailMessage mailMessage) {
        super(mailMessage, template);
    }


    public void setParameters(String username, String question, String answer) {
        this.answer = answer;
        this.question = question;
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if ( username == null || question == null || answer == null )
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();
        context.setVariable("staticTitle", "Bienvenido");
        context.setVariable("staticHello", "Hola!");
        context.setVariable("staticQuestionReceived", "Hemos recibido tu consulta");
        context.setVariable("staticYourQuestion", "Tu consulta - ");
        context.setVariable("staticAnswer", "Respuesta del equipo:");
        context.setVariable("staticRewriteUs", "Puedes volver a escribirnos si lo necesitas.");
        context.setVariable("staticNeedMoreHelpWriteUs", "Necesitas mas ayuda? ");
        context.setVariable("staticWriteUs", "Escribenos!");


        context.setVariable("question", question);
        context.setVariable("username", username);
        context.setVariable("answer", answer);
        return context;
    }
}
