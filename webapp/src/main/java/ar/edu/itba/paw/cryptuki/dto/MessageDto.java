package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Message;
import ar.edu.itba.paw.model.Trade;
import ar.edu.itba.paw.model.User;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class MessageDto {
    private LocalDateTime timestamp;
    private String content;
    private URI sender;

    public static MessageDto fromMessage(final Message message, final UriInfo uriInfo, User seller, User buyer) {

        final MessageDto dto = new MessageDto();

        dto.timestamp = message.getDate();
        dto.content = message.getMessage();

        String sender = seller.getId() == message.getSender() ? seller.getUsername().get() : buyer.getUsername().get();

        dto.sender = uriInfo.getBaseUriBuilder()
                .path("/api/users")
                .path(sender)
                .build();

        return dto;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public URI getSender() {
        return sender;
    }

    public void setSender(URI sender) {
        this.sender = sender;
    }
}
