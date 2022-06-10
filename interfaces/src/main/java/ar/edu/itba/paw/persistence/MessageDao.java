package ar.edu.itba.paw.persistence;

import java.time.LocalDateTime;

public interface MessageDao {
    void sendMessage(Integer sender, Integer trade, String message);
}
