package ar.edu.itba.paw.service;

public interface TradeService {

    final class BuyDigest {
        private int offerId; // TODO -- Question: Should be an Offer or an Offer.Builder?
        private String comments;
        private String buyerEmail;
        private int amount;

        private BuyDigest(int offerId, String buyerEmail, String comments, int amount) {
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

        public static BuyDigest newInstance(int offerId, String buyerEmail, String comments, int amount) {
            return new BuyDigest(offerId, buyerEmail, comments, amount);
        }
    }

    // TODO: Discuss and implement
    final class SellDigest {

        private SellDigest() {

        }

        public static SellDigest newInstance() {
            return new SellDigest();
        }
    }

    void executeTrade(BuyDigest buyDigest);
    void executeTrade(SellDigest sellHelper);


}
