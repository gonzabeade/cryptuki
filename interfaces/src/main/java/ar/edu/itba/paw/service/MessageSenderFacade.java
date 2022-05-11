package ar.edu.itba.paw.service;

public interface MessageSenderFacade {
    void sendWelcomeMessage(String to, String username, int veryCode);
    void sendChangePasswordMessage(String to, String username, int code);
    void sendOfferUploadedMessage(String to, String username, String coinCode, double askingPrice, double minQuantity, double maxQuantity, int offerId);
    void sendComplaintReceipt(String to, String username, String question);
    void sendNewTradeNotification(String to, String username, String coinCode, float quantity, String buyer, String wallet, String buyerMail, int tradeCode);
}
