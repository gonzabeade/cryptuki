package ar.edu.itba.paw.persistence;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Table(name="messages")
public class Message implements Serializable {
    @Id
    @JoinColumn(name="user_id",nullable = false)
    @ManyToOne
    private User sender ;
    @Id
    @ManyToOne
    @JoinColumn(name="trade_id",nullable = false)
    private Trade trade ;
    @Id
    @Column(name="message_content",nullable = false)
    private String message;
    @Id
    @Column(name="message_date",nullable = false)
    private LocalDateTime date;

    private Message(){}

    public static class Builder{

        @Id
        @Column(name="user_id",nullable = false)
        private Integer userId;
        @Column(name="message_content",nullable = false)
        private String message;
        @Column(name="trade_id",nullable = false)
        private Integer tradeId;
        @Column(name="message_date",nullable = false)
        private LocalDateTime date;

        public Builder(String message) {
            this.message = message;
            this.date = LocalDateTime.now();
        }

        public Builder withSender(Integer userId ){
            this.userId = userId;
            return this;
        }

        public Builder withTrade(Integer tradeId) {
            this.tradeId = tradeId;
            return this;
        }

    }


    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
