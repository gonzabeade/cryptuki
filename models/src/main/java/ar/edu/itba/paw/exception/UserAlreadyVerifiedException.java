package ar.edu.itba.paw.exception;

import ar.edu.itba.paw.model.UserStatus;

public class UserAlreadyVerifiedException extends RuntimeException {

    private String username;
    private int userId;
    private UserStatus userStatus;

    public UserAlreadyVerifiedException(String username, UserStatus userStatus) {
        super("The user has already been validated: "+username);
        this.userStatus = userStatus;
        this.username = username;
    }

    public UserAlreadyVerifiedException(int userId, UserStatus userStatus) {
        super("The user has already been validated: userId "+userId);
        this.userStatus = userStatus;
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public UserStatus getUserStatus() {
        return userStatus;
    }
}
