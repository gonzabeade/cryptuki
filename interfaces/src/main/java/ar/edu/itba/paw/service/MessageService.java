package ar.edu.itba.paw.service;

public interface MessageService {
    public void sendMessage(Integer senderId, Integer tradeId, String message);
}
