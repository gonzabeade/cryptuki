package ar.edu.itba.paw.service.mailing;

import org.thymeleaf.context.Context;

public class ChangePasswordThymeleafMailMessage extends ThymeleafMailMessage{
    private final static String template = "NeedHelpTemplate";

    private String username;

    public ChangePasswordThymeleafMailMessage(String from, String to, ThymeleafMailHelper helper) {
        super(from, to, template, helper);
    }
    public ChangePasswordThymeleafMailMessage(MailMessage mailMessage, ThymeleafMailHelper helper) {
        super(mailMessage, template, helper);
    }


    public void setParameters(String username) {
        this.username = username;
    }

    @Override
    protected Context getContext() {

        if (username == null)
            throw new IllegalStateException("Cannot send email with missing parameters");

        Context context = new Context();

        context.setVariable("username", username);
        return context;
    }
}
