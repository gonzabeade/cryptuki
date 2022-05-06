package ar.edu.itba.paw.persistence;

import java.util.Optional;

public final class Complain {

    private final Integer complainId;
    private final ComplainStatus status;
    private final String complainerUsername;
    private final Optional<String> complainerComments;
    private final Optional<String> moderatorUsername;
    private final Optional<String> moderatorComments;
    private final Optional<Integer> tradeId;

    public static class Builder {

        private final String complainerUsername;

        private Integer tradeId;
        private ComplainStatus status = ComplainStatus.PENDING;
        private String complainerComments;
        private String moderatorUsername;
        private String moderatorComments;
        private Integer complainId;


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
        this.complainerComments = Optional.ofNullable(builder.getComplainerComments());
        this.status = builder.status;
        this.moderatorUsername = Optional.ofNullable(builder.getModerator());
        this.moderatorComments = Optional.ofNullable(builder.moderatorComments);
        this.tradeId = Optional.ofNullable(builder.tradeId);
    }

    public Optional<Integer> getTradeId() {
        return tradeId;
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
        return complainerComments;
    }

    public Optional<String> getModerator() {
        return moderatorUsername;
    }

    public Optional<String> getModeratorComments() {
        return moderatorComments;
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
