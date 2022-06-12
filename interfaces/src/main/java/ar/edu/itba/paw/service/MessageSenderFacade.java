package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;

public interface MessageSenderFacade {
    void sendWelcomeMessage(String email, String username, int veryCode);
    void sendChangePasswordMessage(String username, int code);
    void sendOfferUploadedMessage(String username, Offer offer);
    void sendAnonymousComplaintReceipt(String to, String username, String question);
    void sendComplaintReceipt(String username, String question);
    void sendNewTradeNotification(String username, Trade.Builder trade, int tradeId,int offerId);
}
