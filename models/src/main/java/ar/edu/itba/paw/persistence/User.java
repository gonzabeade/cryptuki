package ar.edu.itba.paw.persistence;


import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class User {

    private final int id;
    private final String email;
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

        public Builder(String email) {
            this.email = email;
        }

        public Builder withRatingSum(int sum) {this.ratingSum = sum; return this; }
        public Builder withRatingCount(int count) {this.ratingCount = count; return this; }
        public Builder withId(int id) {this.id = id; return this; }
        public Builder withPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber; return this;}
        public Builder withLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; return this; }

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


        protected User build() {return new User(this);}
    }

    private User (Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.ratingCount = builder.ratingCount;
        this.ratingSum = builder.ratingSum;
        this.phoneNumber= builder.phoneNumber;
        this.lastLogin = builder.lastLogin;
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

    public String getPhoneNumber(){return phoneNumber;}

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }
    public long getMinutesSinceLastLogin(){
        Duration loggedIn = Duration.between(lastLogin, LocalDateTime.now());
        return loggedIn.toMinutes();
    }
}
