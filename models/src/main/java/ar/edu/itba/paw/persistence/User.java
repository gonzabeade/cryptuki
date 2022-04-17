package ar.edu.itba.paw.persistence;


import java.util.HashSet;
import java.util.Set;

public final class User {

    private final Integer id;
    private final String email;
    private final Integer ratingSum;
    private final Integer ratingCount;

    private final Integer phoneNumber;

    public static class Builder {
        private Integer id;
        private String email = "";
        private Integer ratingSum = 0;
        private Integer ratingCount = 0;

        private Integer phoneNumber = 0 ;

        private Builder() { }

        public Builder email(String email) {this.email = email; return this;}
        public Builder ratingSum(int sum) {this.ratingSum = sum; return this; }
        public Builder ratingCount(int count) {this.ratingCount = count; return this; }
        public Builder id(int id) {this.id = id; return this; }

        public Builder phoneNumber(int phoneNumber){this.phoneNumber = phoneNumber;return this;}

        protected User build() {return new User(this);}

        public Integer getId() {
            return id;
        }
        public String getEmail() {
            return email;
        }
        public Integer getRatingSum() {
            return ratingSum;
        }
        public Integer getRatingCount() {
            return ratingCount;
        }

        public Integer getPhoneNumber(){return phoneNumber;}
    }

    public static Builder builder() {
        return new Builder();
    }

    private User (Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.ratingCount = builder.ratingCount;
        this.ratingSum = builder.ratingSum;
        this.phoneNumber= builder.phoneNumber;
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

    public int phoneNumber(){return phoneNumber;}

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
