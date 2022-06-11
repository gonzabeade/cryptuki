package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.model.Role;
import ar.edu.itba.paw.model.UserStatus;

import javax.persistence.*;

@Entity
@Table(name = "auth")
public final class UserAuth  {

    @Id
    @Column(name="user_id")
    private Integer userId;
    @Column(name="uname", nullable = false, length = 50, unique = true)
    private String username;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(name = "role_id")
    @Enumerated(EnumType.ORDINAL)
    private Role role = Role.USER;

    @Column(name="code")
    private Integer code;
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private UserStatus userStatus;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public UserAuth(){
        // Just for Hibernate
    }

    public static class Builder {
        private Integer id;
        private String username;
        private String password;
        private Integer roleId = 2;

        private Integer code;
        private UserStatus userStatus = UserStatus.UNVERIFIED;


        public Builder(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public Builder withId(int id) { this.id = id; return this; }
        public Builder withPassword(String password){this.password = password; return this; }
        public Builder withCode(Integer code){this.code = code ; return this;}
        public Builder withUserStatus(UserStatus userStatus){this.userStatus=userStatus; return this;}


        public int getId() {
            return id;
        }
        public String getUsername() {
            return username;
        }
        public String getPassword() {
            return password;
        }
        public int getCode() {
            return code;
        }
        public UserStatus getUserStatus() {
            return userStatus;
        }
        protected UserAuth build() {
            return new UserAuth(this);
        }
    }

    private UserAuth(Builder builder) {
        this.username = builder.username;
        this.password = builder.password;
        this.code = builder.code;
        this.userStatus = builder.userStatus;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }

    public Integer getCode() {
        return code;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }

    public User getUser() {
        return user;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserStatus(UserStatus userStatus) {
        this.userStatus = userStatus;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}