package ar.edu.itba.paw.persistence;

import java.time.LocalDate;
import java.util.Optional;

public final class Complain {

    private final Integer complainId;
    private final ComplainStatus status;
    private final String complainerUsername;

    private final LocalDate date;
    private final String complainerComments;
    private final String moderatorUsername;
    private final String moderatorComments;
    private final Integer tradeId;

    public static class Builder {

        private final String complainerUsername;

        private Integer tradeId;
        private ComplainStatus status = ComplainStatus.PENDING;
        private String complainerComments;
        private String moderatorUsername;
        private String moderatorComments;
        private Integer complainId;

        private LocalDate date;


        public Builder(String complainerUsername) {
            this.complainerUsername = complainerUsername;
        }

        public Builder withComplainId(Integer complainId) {
            this.complainId = complainId;
            return this;
        }

        public Builder withTradeId(Integer tradeId) {
            this.tradeId = tradeId;
            return this;
        }

        protected Builder withDate(LocalDate date) {
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
        this.complainerUsername = builder.complainerUsername;
        this.complainId = builder.complainId;
        this.complainerComments = builder.getComplainerComments();
        this.status = builder.status;
        this.moderatorUsername = builder.getModerator();
        this.moderatorComments = builder.moderatorComments;
        this.tradeId = builder.tradeId;
        this.date = builder.date;
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

    public Optional<String> getComplainerComments() {
        return Optional.ofNullable(complainerComments);
    }

    public Optional<String> getModerator() {
        return Optional.ofNullable(moderatorUsername);
    }

    public Optional<String> getModeratorComments() {
        return Optional.ofNullable(moderatorComments);
    }

    public String getComplainerUsername() {
        return complainerUsername;
    }

    public LocalDate getDate() {
        return date;
    }

    public Optional<String> getModeratorUsername() {
        return Optional.ofNullable(moderatorUsername);
    }

    public Optional<Integer> getTradeId() {
        return Optional.ofNullable(tradeId);
    }

    @Override
    public String toString() {
        return "Complain{" +
                "complainId=" + complainId +
                ", status=" + status +
                ", complainerUsername='" + complainerUsername + '\'' +
                ", complainerComments=" + complainerComments +
                ", moderatorUsername=" + moderatorUsername +
                ", moderatorComments=" + moderatorComments +
                ", tradeId=" + tradeId +
                '}';
    }
}
