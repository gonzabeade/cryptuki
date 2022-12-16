package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Trade;

import javax.ws.rs.core.UriInfo;
import java.time.LocalDateTime;

public class MessageDto {
    private LocalDateTime dateTime;
    private String message;
    private int sender;

    public static MessageDto fromMessage(final Message message, final UriInfo uriInfo) {

        final MessageDto dto = new MessageDto();

        dto.setDateTime(message.getDate());
        dto.setMessage(message.getMessage());
        dto.setSender(message.getSender());

        return dto;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getSender() {
        return sender;
    }

    public void setSender(int sender) {
        this.sender = sender;
    }
}
