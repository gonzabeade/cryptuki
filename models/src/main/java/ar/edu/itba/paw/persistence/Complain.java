package ar.edu.itba.paw.persistence;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;
@Entity
@Table(name="complain")
public final class Complain {

    Complain(){}

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complain_complain_id_seq")
    @SequenceGenerator(sequenceName = "complain_complain_id_seq", name = "complain_complain_id_seq", allocationSize = 1)
    @Column(name="complain_id")
    private  Integer complainId;
    @OneToOne
    @JoinColumn(name="trade_id")
    private  Trade trade;

    @Column(name="status",nullable = false)
    @Enumerated(EnumType.STRING)
    private  ComplainStatus status;

    @OneToOne
    @JoinColumn(name="complainer_id")
    private User complainer;

    @OneToOne
    @JoinColumn(name="moderator_id")
    private User moderator;

    @Column(name="complain_date")
    private  LocalDateTime date;
    @Column(name="complainer_comments",nullable = false)
    private  String complainerComments;
    @Column(name="moderator_comments",nullable = false)
    private  String moderatorComments;


    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    @Entity
    @Table(name="complain")
    public static class Builder {


        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complain_complain_id_seq")
        @SequenceGenerator(sequenceName = "complain_complain_id_seq", name = "complain_complain_id_seq", allocationSize = 1)
        @Column(name="complain_id")
        private Integer complainId;

        @Column(name="trade_id")
        private Integer tradeId;

        @Column(name="complainer_id",nullable = false)
        private Integer complainerId;


        @Column(name="status",nullable = false)
        @Enumerated(EnumType.STRING)
        private ComplainStatus status = ComplainStatus.PENDING;
        @Column(name="complainer_comments",nullable = false)
        private String complainerComments;
        @Column(name="complain_date")
        private LocalDateTime date;
        @Transient
        private String moderatorUsername;
        @Transient
        private String moderatorComments;

        @Transient
        private final String complainerUsername;



        public Builder(String complainerUsername) {
            this.complainerUsername = complainerUsername;
            this.date = LocalDateTime.now();
        }

        public Builder withComplainId(Integer complainId) {
            this.complainId = complainId;
            return this;
        }

        public Builder withTradeId(Integer tradeId) {
            this.tradeId = tradeId;
            return this;
        }
        public Builder withComplainerId(Integer complainerId) {
            this.complainerId = complainerId;
            return this;
        }

        protected Builder withDate(LocalDateTime date) {
            this.date = date;
            return this;
        }

        public Builder withComplainerComments(String complainerComments) {
            this.complainerComments = complainerComments;
            return this;
        }
        public Builder withModerator(String moderatorUsername) {
            this.moderatorUsername = moderatorUsername;
            return this;
        }
        public Builder withModeratorComments(String moderatorComments) {
            this.moderatorComments = moderatorComments;
            return this;
        }
        public Builder withComplainStatus(ComplainStatus complainStatus) {
            this.status = complainStatus;
            return this;
        }

        public String getModerator() {
            return moderatorUsername;
        }
        public String getModeratorComments() {
            return moderatorComments;
        }
        public Integer getComplainId() {
            return complainId;
        }
        public ComplainStatus getStatus() {
            return status;
        }
        public String getComplainer() {
            return complainerUsername;
        }
        public String getComplainerComments() {
            return complainerComments;
        }
        public Integer getTradeId() {return tradeId; }

        protected Complain build() {
            return new Complain(this);
        }
    }

    private Complain(Complain.Builder builder) {
        this.complainId = builder.complainId;
        this.complainerComments = builder.getComplainerComments();
        this.status = builder.status;
        this.moderatorComments = builder.moderatorComments;
        this.date = builder.date;
    }


    public Integer getComplainId() {
        return complainId;
    }

    public ComplainStatus getStatus() {
        return status;
    }

    public String getComplainer() {
        return complainer.getUserAuth().getUsername();
    }

    public Optional<String> getComplainerComments() {
        return Optional.ofNullable(complainerComments);
    }

    public Optional<String> getModerator() {
        return Optional.ofNullable(moderator.getUserAuth().getUsername());
    }

    public Optional<String> getModeratorComments() {
        return Optional.ofNullable(moderatorComments);
    }

    public String getComplainerUsername() {
        return complainer.getUserAuth().getUsername();
    }

    public String getDate() {
        return date.format(ISO_LOCAL_DATE);
    }

    public Optional<String> getModeratorUsername() {
        return Optional.ofNullable(moderator.getUserAuth().getUsername());
    }

    public Optional<Integer> getTradeId() {
        return Optional.ofNullable(trade.getTradeId());
    }

//    @Override
//    public String toString() {
//        return "Complain{" +
//                "complainId=" + complainId +
//                ", status=" + status +
//                ", complainerUsername='" + complainerUsername + '\'' +
//                ", complainerComments=" + complainerComments +
//                ", moderatorUsername=" + moderatorUsername +
//                ", moderatorComments=" + moderatorComments +
//                ", tradeId=" + tradeId +
//                '}';
//    }

    public boolean equals(Object object) {
        if (object == this)
            return true;
        if (!(object instanceof Complain))
            return false;
        Complain testedComplain = (Complain) object;
        return testedComplain.getComplainId() == this.getComplainId();
    }


    public void setComplainer(User complainer) {
        this.complainer = complainer;
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public void setComplainId(Integer complainId) {
        this.complainId = complainId;
    }


    public void setStatus(ComplainStatus status) {
        this.status = status;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setComplainerComments(String complainerComments) {
        this.complainerComments = complainerComments;
    }

    public void setModeratorComments(String moderatorComments) {
        this.moderatorComments = moderatorComments;
    }
}
