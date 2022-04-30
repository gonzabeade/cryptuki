package ar.edu.itba.paw.service.digests;

public final class BuyDigest {
    private final int offerId; // TODO -- Question: Should be an Offer or an Offer.Builder?
    private final String comments;
    private final String buyerEmail;
    private final int amount;

    public BuyDigest(int offerId, String buyerEmail, String comments, int amount) {
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
    public int getAmount() {
        return amount;
    }
}