package ar.edu.itba.paw.service.digests;

public final class SupportDigest {

    private final String author; // TODO -- Question: Should be an instance of User?
    private final String body;

    public SupportDigest(String body, String author) {
        this.body = body;
        this.author = author;
    }

    public String getBody() { return body; }
    public String getAuthor() { return this.author; }
}
