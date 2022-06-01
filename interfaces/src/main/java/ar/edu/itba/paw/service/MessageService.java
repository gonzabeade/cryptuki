package ar.edu.itba.paw.service;
import ar.edu.itba.paw.persistence.Message;
public interface MessageService {
    void sendMessage(Message.Builder builder);
}
