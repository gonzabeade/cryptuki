package ar.edu.itba.paw.persistence;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public final class User {

    private final Integer id;
    private final UserAuth userAuth;
    private final String email;
    private final Integer ratingSum;
    private final Integer ratingCount;
    private final Collection<Role> roles;

    public static class Builder {
        private Integer id;
        private UserAuth userAuth;
        private String email = "";
        private Integer ratingSum = 0;
        private Integer ratingCount = 0;
        private Set<Role> roles = new HashSet<>();

        private Builder() { }

        public Builder userAuth(UserAuth userAuth) {this.userAuth = userAuth; return this; }
        public Builder email(String email) {this.email = email; return this;}
        public Builder ratingSum(int sum) {this.ratingSum = sum; return this; }
        public Builder ratingCount(int count) {this.ratingCount = count; return this; }
        public Builder addRole(Role role) { roles.add(role); return this; }
        public Builder id(int id) {this.id = id; return this; }

        protected User build() {return new User(this);}

        public Integer getId() {
            return id;
        }
        public UserAuth getUserAuth() {
            return userAuth;
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
    }

    public static Builder builder() {
        return new Builder();
    }

    private User (Builder builder) {
        this.id = builder.id;
        this.email = builder.email;
        this.ratingCount = builder.ratingCount;
        this.ratingSum = builder.ratingSum;
        this.roles = builder.roles;
        this.userAuth = builder.userAuth;
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
    public String getUsername() { return userAuth.getUsername(); }

    // The user knows it is a defensive copy what is being returned
    public Collection<Role> getRoles() {
        Collection<Role> roles = new HashSet<>();
        roles.addAll(this.roles);
        return roles;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                '}';
    }
}
