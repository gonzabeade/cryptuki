package ar.edu.itba.paw.service.digests;

public final class BuyDigest {
    private final int offerId;
    private final String comments;
    private final String buyerEmail;
    private final double amount;

    public BuyDigest(int offerId, String buyerEmail, String comments, double amount) {
        this.offerId = offerId;
        this.comments = comments;
        this.buyerEmail = buyerEmail;
        this.amount = amount;
    }

    public int getOfferId() {
        return offerId;
    }
    public String getComments() {
        return comments;
    }
    public String getBuyerEmail() {
        return buyerEmail;
    }
    public double getAmount() {
        return amount;
    }
}