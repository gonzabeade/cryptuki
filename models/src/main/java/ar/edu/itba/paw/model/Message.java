package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name="messages")
public class Message implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "messages_message_id_seq")
    @SequenceGenerator(sequenceName = "messages_message_id_seq", name = "messages_message_id_seq", allocationSize = 1)
    @Column(name="message_id", nullable = false)
    private int messageId;
    @Column(name="user_id", nullable = false)
    private int sender;
    @Column(name="trade_id", nullable = false)
    private int trade ;
    @Column(name="message_content", nullable = false)
    private String message;
    @Column(name="message_date", nullable = false, insertable = false)
    private LocalDateTime date;

    public Message(){
        // Just for Hibernate
    }

    public Message(Integer sender, Integer trade, String message) {
        this.sender = sender;
        this.trade = trade;
        this.message = message;
        this.date = LocalDateTime.now();
    }

    public int getSender() {
        return sender;
    }

    public int getTrade() {
        return trade;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getDate() {
        return date;
    }
}
