package ar.edu.itba.paw.service;

public interface TradeService {

    final class BuyHelper {
        private int offerId; // TODO -- Question: Should be an Offer or an Offer.Builder?
        private String comments;
        private String buyerEmail;
        private int amount;

        private BuyHelper(int offerId, String buyerEmail, String comments, int amount) {
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

        public static BuyHelper newInstance(int offerId, String buyerEmail, String comments, int amount) {
            return new BuyHelper(offerId, buyerEmail, comments, amount);
        }
    }

    // TODO: Discuss and implement
    final class SellHelper {

        private SellHelper() {

        }

        public static SellHelper newInstance() {
            return new SellHelper();
        }
    }

    void executeTrade(BuyHelper buyHelper);
    void executeTrade(SellHelper sellHelper);


}
