package ar.edu.itba.paw.exception;

public class NoSuchKycException extends RuntimeException {

    private String username;
    private int kycId;

    public NoSuchKycException(int kycId) {
        super("Kyc with id does not exist. Id: "+kycId);
        this.kycId = kycId;
    }

    public NoSuchKycException(String kycUsername) {
        super("No pending Kyc Request for"+kycUsername);
        this.username = kycUsername;
    }

    public String getUsername() {
        return username;
    }

    public int getKycId() {
        return kycId;
    }
}
