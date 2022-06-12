package ar.edu.itba.paw.model;

import ar.edu.itba.paw.persistence.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
// TODO(gonza) check cardinality of relations
@Entity
@Table(name="complain")
public class Complain {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "complain_complain_id_seq")
    @SequenceGenerator(sequenceName = "complain_complain_id_seq", name = "complain_complain_id_seq", allocationSize = 1)
    @Column(name="complain_id")
    private Integer complainId;
    @OneToOne
    @JoinColumn(name="trade_id")
    private Trade trade;
    @Column(name="status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ComplainStatus status = ComplainStatus.PENDING;
    @OneToOne
    @JoinColumn(name="complainer_id")
    private User complainer;
    @OneToOne
    @JoinColumn(name="moderator_id")
    private User moderator;
    @Column(name="complain_date", nullable = false, insertable = false)
    private LocalDateTime date;
    @Column(name="complainer_comments")
    private String complainerComments;
    @Column(name="moderator_comments")
    private String moderatorComments;

    public static class Builder {

        private Trade trade;
        private User complainer;
        private ComplainStatus status;
        private User moderator;
        private String moderatorComments;
        private String complainerComments;

        public Builder(Trade trade, User complainer) {
            this.trade = trade;
            this.complainer = complainer;
        }

        public Builder withStatus(ComplainStatus status) {
            this.status = status;
            return this;
        }

        public Builder withModerator(User moderator) {
            this.moderator = moderator;
            return this;
        }

        public Builder withComplainerComments(String complainerComments) {
            this.complainerComments = complainerComments;
            return this;
        }

        public Builder withModeratorComments(String moderatorComments) {
            this.moderatorComments = moderatorComments;
            return this;
        }

        public Complain build() {
            return new Complain(this);
        }
    }

    private Complain(Complain.Builder builder) {
        this.complainerComments = builder.complainerComments;
        this.moderatorComments = builder.moderatorComments;
        this.complainer = builder.complainer;
        this.moderator = builder.moderator;
        this.trade = builder.trade;
        this.status = builder.status;
    }

    public Complain() {
        // Just for Hibernate!
    }

    public Integer getComplainId() {
        return complainId;
    }

    public Trade getTrade() {
        return trade;
    }

    public ComplainStatus getStatus() {
        return status;
    }

    public User getComplainer() {
        return complainer;
    }

    public Optional<User> getModerator() {
        return Optional.ofNullable(moderator);
    }

    public LocalDateTime getDate() {
        return date;
    }

    public Optional<String> getComplainerComments() {
        return Optional.ofNullable(complainerComments);
    }

    public Optional<String> getModeratorComments() {
        return Optional.ofNullable(moderatorComments);
    }

    public void setModerator(User moderator) {
        this.moderator = moderator;
    }

    public void setModeratorComments(String moderatorComments) {
        this.moderatorComments = moderatorComments;
    }

    public void setStatus(ComplainStatus status) {
        this.status = status;
    }
}
