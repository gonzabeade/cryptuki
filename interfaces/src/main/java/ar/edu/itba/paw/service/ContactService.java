package ar.edu.itba.paw.service;

import ar.edu.itba.paw.service.Message;

public interface ContactService<T extends Message> {

    void sendMessage(T message);
    T createMessage(String from, String to);
}
