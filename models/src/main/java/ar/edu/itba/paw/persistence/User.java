package ar.edu.itba.paw.persistence;


public final class User {

    private final int id;
    private final String email;
    private final int ratingSum;
    private final int ratingCount;

    private final String phoneNumber;

    public static class Builder {

        private final String email;

        private Integer id;
        private int ratingSum = 0;
        private int ratingCount = 0;

        private String phoneNumber;

        public Builder(String email) {
            this.email = email;
        }

        public Builder withRatingSum(int sum) {this.ratingSum = sum; return this; }
        public Builder withRatingCount(int count) {this.ratingCount = count; return this; }
        public Builder withId(int id) {this.id = id; return this; }
        public Builder withPhoneNumber(String phoneNumber){this.phoneNumber = phoneNumber;return this;}

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

        protected User build() {return new User(this);}
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

    public String phoneNumber(){return phoneNumber;}

}
