package ar.edu.itba.paw.service;

public interface ChatService {
    void sendMessage(Integer senderId, Integer tradeId, String message);
    void markBuyerMessagesAsSeen(int tradeId);
    void markSellerMessagesAsSeen(int tradeId);
}
