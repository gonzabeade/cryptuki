package ar.edu.itba.paw;

public class UserAuth{

    private int id, sessionId;
    private String username, password;

    public class Builder {
        private int id, sessionId;
        private String username, password;

        public Builder(int id, String username, String password) {
            this.username = username;
            this.password = password;
            this.id = id;
        }

        public Builder id(int id) { this.id = id; return this; }
        public Builder username(String username) { this.username = username; return this; }
        public Builder password(String password) { this.password = password; return this; }
        public Builder sessionId(int sessionId) {this.sessionId = sessionId; return this; }
        public UserAuth build() {
            return new UserAuth(this);
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
    public String getPassword() {
        return password;
    }
}