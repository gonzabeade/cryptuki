package ar.edu.itba.paw.model;

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
    private UserStatus userStatus = UserStatus.UNVERIFIED;
    @OneToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    public UserAuth(){
        // Just for Hibernate
    }

    public UserAuth(int userId, String username, String password, int verificationCode) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.code = verificationCode;
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