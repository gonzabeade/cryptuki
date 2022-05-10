package ar.edu.itba.paw.exception;

public class NoSuchUserException extends RuntimeException {

    private String username;

    public NoSuchUserException(String username) {
        super("User with username does not exist. Username: "+username);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
