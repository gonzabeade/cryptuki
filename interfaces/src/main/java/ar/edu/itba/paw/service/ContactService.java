package ar.edu.itba.paw.service;

public interface ContactService<T extends Message> {

    void sendMessage(T message);
    T createMessage(String from, String to);
}
