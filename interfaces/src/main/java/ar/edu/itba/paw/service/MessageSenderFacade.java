package ar.edu.itba.paw.service;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;

public interface MessageSenderFacade {

    void sendWelcomeMessage(User user, int veryCode); // Call to action - verify account POST
    void sendForgotPasswordMessage(User user, int code); // Call to action - change password anonymously
    void sendOfferUploadedMessage(User user, Offer offer); // Call to action - seeoffer
    void sendAnonymousComplaintReceipt(String to, String question);
    void sendComplaintReceipt(User user, Trade trade, String complaint); // Call to action - maybe if My Complaints
    void sendComplainClosedWithKickout(User user, String reason);
    void sendComplainClosedWithDisesteem(User user, String reason);
    void sendYouWereKickedOutBecause(User user, String reason);
    void sendNewTradeNotification(Trade trade); // Call to action - Chat/tradeId
    void sendNewUnseenMessages(Trade trade, User user); // Call to action - Chat/tradeId

}
