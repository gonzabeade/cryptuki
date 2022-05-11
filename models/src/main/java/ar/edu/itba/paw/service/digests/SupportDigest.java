package ar.edu.itba.paw.service.digests;

public final class SupportDigest {

    private final String author;
    private final String body;

    public SupportDigest(String body, String author) {
        this.body = body;
        this.author = author;
    }

    public String getBody() { return body; }
    public String getAuthor() { return this.author; }
}
