package ar.edu.itba.paw.persistence;

public interface MessageDao {
    void sendMessage(Message.Builder builder);
}
