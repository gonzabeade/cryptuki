package ar.edu.itba.paw;

public class User {

    private int id;
    private String email;
    private int ratingSum;
    private int ratingCount;

    public class Builder {
        private int id;
        private String email = "";
        private int ratingSum = 0;
        private int ratingCount = 0;

        public Builder(int id) { this.id = id; }
        public Builder email(String email) {this.email = email; return this;}
        public Builder ratingSum(int sum) {this.ratingSum = sum; return this; }
        public Builder ratingCount(int count) {this.ratingCount = count; return this; }

        public User build() {return new User(this);}
    }

    private User (Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.ratingCount = builder.ratingCount;
        this.ratingSum = builder.ratingSum;
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


}
