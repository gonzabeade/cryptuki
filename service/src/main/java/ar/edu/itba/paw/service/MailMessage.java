package ar.edu.itba.paw.service;


// TODO: Validate. Do not leave MailMessage in a inconsistent state
public class MailMessage implements Message {

    private String from;
    private String to;
    private String subject = "";
    private String body = "";

    protected MailMessage(String from, String to) {
        this.from = from;
        this.to = to;
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

}