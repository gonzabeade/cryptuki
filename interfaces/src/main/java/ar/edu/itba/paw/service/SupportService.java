package ar.edu.itba.paw.service;


public interface SupportService {

    final class Helper {

        private String title;
        private String author; // TODO -- Question: Should be an instance of User?
        private String body;

        private Helper() {
        }

        public Helper title(String title) { this.title = title; return null; }
        public Helper body(String body) { this.body = body; return this; }
        public Helper author(String author) {this.author = author; return this; }

        public String getBody() { return body; }
        public String getAuthor() { return this.author; }
        public String getTitle() { return this.title; }

        public static Helper newInstance() {
            return new Helper();
        }

    }

    void getSupportFor(Helper helper);
}
