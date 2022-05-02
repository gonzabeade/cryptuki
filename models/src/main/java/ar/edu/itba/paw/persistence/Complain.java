package ar.edu.itba.paw.persistence;

import java.util.Optional;

public final class Complain {

    private final int complainId;
    private final ComplainStatus status;
    private final String complainerUsername;
    private final Optional<String> complainerComments;
    private final Optional<String> moderatorUsername;
    private final Optional<String> moderatorComments;

    public static class Builder {

        private final int complainId;
        private final String complainerUsername;

        private ComplainStatus status = ComplainStatus.PENDING;
        private String complainerComments;
        private String moderatorUsername;
        private String moderatorComments;

        public Builder(int complainId, String complainerUsername) {
            this.complainId = complainId;
            this.complainerUsername = complainerUsername;
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
        public int getComplainId() {
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
    }

    public int getComplainId() {
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
}
