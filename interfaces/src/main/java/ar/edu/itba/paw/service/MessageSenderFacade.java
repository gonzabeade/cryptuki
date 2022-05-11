package ar.edu.itba.paw.service;

public interface MessageSenderFacade {
    void sendWelcomeMessage(String username, int veryCode);
    void sendChangePasswordMessage(String username, int code);
    void sendOfferUploadedMessage(String username, String coinCode, float askingPrice, float minQuantity, float maxQuantity, int offerId);
    void sendComplaintReceipt(String username, String question);
    void sendNewTradeNotification(String username, String coinCode, String quantity, String buyer, String wallet, String buyerMail, String tradeCode);
}
