package ar.edu.itba.paw.exception;

public class NoSuchUserException extends RuntimeException {

    private String username;
    private int userId;

    public NoSuchUserException(String username) {
        super("User with username does not exist. Username: "+username);
        this.username = username;
    }

    public NoSuchUserException(int userId) {
        super("User with username does not exist. userId: "+userId);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }
}
