package ar.edu.itba.paw.exception;

public class NoSuchKycException extends RuntimeException {

    private int kycId;

    public NoSuchKycException(int kycId) {
        super("Kyc with id does not exist. Id: "+kycId);
        this.kycId = kycId;
    }

    public int getKycId() {
        return kycId;
    }
}
