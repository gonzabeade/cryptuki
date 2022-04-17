package ar.edu.itba.paw.service;


public interface SupportService {

    final class Digest {

        private String title;
        private String author; // TODO -- Question: Should be an instance of User?
        private String body;

        private Digest() {
        }

        public Digest title(String title) { this.title = title; return this; }
        public Digest body(String body) { this.body = body; return this; }
        public Digest author(String author) {this.author = author; return this; }

        public String getBody() { return body; }
        public String getAuthor() { return this.author; }
        public String getTitle() { return this.title; }

        public static Digest newInstance() {
            return new Digest();
        }
    }

    void getSupportFor(Digest digest);
}
