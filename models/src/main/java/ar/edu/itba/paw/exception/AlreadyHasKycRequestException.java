package ar.edu.itba.paw.exception;

public class AlreadyHasKycRequestException extends RuntimeException {

    private String username;

    public AlreadyHasKycRequestException(String username) {
        super(String.format("User `%s` already has an active KYC filing.", username));
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
