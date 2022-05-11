package ar.edu.itba.paw.persistence;


import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

public final class User {

    private final int id;
    private final String email;
    private final String username;

    private final int ratingSum;
    private final int ratingCount;

    private final LocalDateTime lastLogin;
    private final String phoneNumber;


    public static class Builder {

        private final String email;

        private Integer id;
        private int ratingSum = 0;
        private int ratingCount = 0;

        private LocalDateTime lastLogin = LocalDateTime.now();

        private String phoneNumber;
        private String username;

        public Builder(String email) {
            this.email = email;
        }

        public Builder withRatingSum(int sum) {this.ratingSum = sum; return this; }
        public Builder withRatingCount(int count) {this.ratingCount = count; return this; }
        public Builder withId(int id) {this.id = id; return this; }
        public Builder withPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber; return this;}
        public Builder withLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; return this; }
        public Builder withUsername(String username) {this.username = username; return this; }

        public Integer getId() {
            return id;
        }
        public String getEmail() {
            return email;
        }
        public int getRatingSum() {
            return ratingSum;
        }
        public int getRatingCount() {
            return ratingCount;
        }
        public String getPhoneNumber(){return phoneNumber;}
        public LocalDateTime getLastLogin(){return lastLogin;}

        public String getUsername() {
            return username;
        }

        protected User build() {return new User(this);}
    }

    private User (Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.ratingCount = builder.ratingCount;
        this.ratingSum = builder.ratingSum;
        this.phoneNumber= builder.phoneNumber;
        this.lastLogin = builder.lastLogin;
        this.username = builder.username;
    }

    public int getId() {
        return id;
    }
    public String getEmail() {
        return email;
    }
    public int getRatingSum() {
        return ratingSum;
    }
    public int getRatingCount() {
        return ratingCount;
    }

    public float getRating() { return getRatingCount() == 0 ? 0 : (float)getRatingSum() / getRatingCount(); }

    public String getPhoneNumber(){return phoneNumber;}

    public Optional<String> getUsername() {
        return Optional.ofNullable(username);
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }


    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof User))
            return false;
        User testedUser = (User) object;
        return testedUser.getId() == this.getId();
    }

    public long getMinutesSinceLastLogin(){
        Duration loggedIn = Duration.between(lastLogin, LocalDateTime.now());
        return loggedIn.toMinutes();
    }
}
