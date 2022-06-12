package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;

public interface MessageDao {
    void sendMessage(int sender, int trade, String message);
}
