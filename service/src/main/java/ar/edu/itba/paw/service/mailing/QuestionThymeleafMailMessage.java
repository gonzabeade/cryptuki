package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class QuestionThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "QuestionTemplate";

    private String username;
    private String question;

    public QuestionThymeleafMailMessage(String from, String to, ThymeleafProcessor helper) {
        super(from, to, template, helper);
    }
    public QuestionThymeleafMailMessage(MailMessage mailMessage, ThymeleafProcessor helper) {
        super(mailMessage, template, helper);
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

        context.setVariable("question", question);
        context.setVariable("username", username);
        return context;
    }
}
