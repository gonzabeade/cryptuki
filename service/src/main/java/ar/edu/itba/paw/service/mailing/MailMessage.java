package ar.edu.itba.paw.service.mailing;
import ar.edu.itba.paw.service.Message;

public class MailMessage implements Message {

    private String from;
    private String to;
    private String subject = "";
    private String body = "";

    private boolean isHtml = false;

    public MailMessage(String from, String to) {
        this.from = from;
        this.to = to;
    }

    public MailMessage(MailMessage mailMessage) {
        this.from = mailMessage.from;
        this.isHtml = mailMessage.isHtml;
        this.to = mailMessage.to;
        this.body = mailMessage.body;
    }

    @Override
    public String getFrom() {
        return from;
    }
    @Override
    public String getTo() {
        return to;
    }
    public String getSubject() {
        return subject;
    }
    public String getBody() {
        return body;
    }

    public void setSubject (String subject) { this.subject = subject; }
    public void setBody (String body) { this.body = body; }


    public void setHtml(boolean isHtml) {
        this.isHtml = isHtml;
    }
    public boolean isHtml() { return isHtml; }

}