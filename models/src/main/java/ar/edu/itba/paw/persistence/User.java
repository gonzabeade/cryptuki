package ar.edu.itba.paw.persistence;


import javax.persistence.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;

@Entity
@Table(name="users")
public final class User {

    User(){}
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    private  int id;
    @Column(length = 50,unique = true,nullable = false)
    private  String email;

    @Column(name="rating_sum",nullable = false)
    private  int ratingSum;
    @Column(name="rating_count",nullable = false)
    private  int ratingCount;

    @Column(name="rating", nullable = false, insertable = false, updatable = false)
    private float rating;

    @Column(name="last_login")
    private LocalDateTime lastLogin;
    @Column(name="phone_number",length = 10)
    private  String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY , mappedBy = "user")
    private UserAuth userAuth;


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
//        this.id = builder.id;
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

    public float getRating(){
        return rating;
    }

    public String getPhoneNumber(){return phoneNumber;}

    public Optional<String> getUsername() {
        if(userAuth!=null)
            return Optional.ofNullable(userAuth.getUsername());
        return Optional.empty();
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public UserAuth getUserAuth() {
        return userAuth;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRatingSum(int ratingSum) {
        this.ratingSum = ratingSum;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setUserAuth(UserAuth userAuth) {
        this.userAuth = userAuth;
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
