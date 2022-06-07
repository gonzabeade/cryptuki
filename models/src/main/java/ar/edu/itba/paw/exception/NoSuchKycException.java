package ar.edu.itba.paw.exception;

public class NoSuchKycException extends RuntimeException {

    public NoSuchKycException(int kycId) {
        super("Kyc with id does not exist. Id: "+kycId);
    }

    public NoSuchKycException(String kycUsername) {
        super("Kyc with id does not exist. Id: "+kycUsername);
    }

}
