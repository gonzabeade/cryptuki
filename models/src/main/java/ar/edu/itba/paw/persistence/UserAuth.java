package ar.edu.itba.paw.persistence;

public final class UserAuth{

    private Integer id;
    private Integer sessionId;
    private String password;
    private String username;  // Does not contain password. Password from database should never be loaded to Server RAM.

    public static class Builder {
        private Integer id;
        private Integer sessionId;
        private String username;
        private String password;

        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder id(int id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder sessionId(int sessionId) {this.sessionId = sessionId; return this; }

        public UserAuth build() {
            return new UserAuth(this);
        }

        public Integer getId() {
            return id;
        }
        public Integer getSessionId() {
            return sessionId;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
    }

    private UserAuth(Builder builder) {
        this.id = builder.id;
        this.username = builder.username;
        this.password = builder.password;
    }

    public int getId() {
        return id;
    }
    public String getUsername() {
        return username;
    }

    public String getPassword(){return password;}
}