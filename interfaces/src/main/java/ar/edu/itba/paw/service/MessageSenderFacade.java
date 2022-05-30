package ar.edu.itba.paw.service;
import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.persistence.Trade;

public interface MessageSenderFacade {
    void sendWelcomeMessage(String email, String username, int veryCode);
    void sendChangePasswordMessage(String username, int code);
    void sendOfferUploadedMessage(String username, OfferDigest offerDigest, int offerId);
    void sendAnonymousComplaintReceipt(String to, String username, String question);
    void sendComplaintReceipt(String username, String question);

    void sendNewTradeNotification(String username, Trade.Builder trade, int tradeId,int offerId);


}
