package ar.edu.itba.paw.service;

public interface ContactService {

    interface Message {
        String getTo();
        String getFrom();
    }

    void sendMessage(Message m);
    Message newMessage();
}
