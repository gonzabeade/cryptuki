package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class NeedHelpThymeleafMailMessage extends ThymeleafMailMessage {

    private final static String template = "NeedHelpTemplate";

    private String username;
    private String question;
    private String answer;

    public NeedHelpThymeleafMailMessage(String from, String to, ThymeleafMailHelper helper) {
        super(from, to, template, helper);
    }
    public NeedHelpThymeleafMailMessage(MailMessage mailMessage, ThymeleafMailHelper helper) {
        super(mailMessage, template, helper);
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

        context.setVariable("question", question);
        context.setVariable("username", username);
        context.setVariable("answer", answer);
        return context;
    }
}
