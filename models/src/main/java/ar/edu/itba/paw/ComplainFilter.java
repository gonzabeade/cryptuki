package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.ComplainStatus;


import java.util.Optional;

public class ComplainFilter {

    private final int page;
    private final int pageSize;
    private final Optional<String> complainerUsername;
    private final Optional<ComplainStatus> complainStatus;
    private final Optional<String> moderatorUsername;
    private final Optional<Integer> tradeId;

    private final Optional<Integer> complainId;


    public static final class Builder {
        private int page = 0;
        private int pageSize = 1;
        private String complainerUsername;
        private ComplainStatus complainStatus;
        private String moderatorUsername;
        private Integer tradeId;

        private Integer complainId;

        public Builder withPage(int page) {
            this.page = page;
            return this;
        }

        public Builder withPageSize(int pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public Builder withComplainerUsername(String complainerUsername) {
            this.complainerUsername = complainerUsername;
            return this;
        }

        public Builder withComplainStatus(ComplainStatus complainStatus) {
            this.complainStatus = complainStatus;
            return this;
        }

        public Builder withModeratorUsername(String moderatorUsername) {
            this.moderatorUsername = moderatorUsername;
            return this;
        }

        public Builder withTradeId(int tradeId) {
            this.tradeId = tradeId;
            return this;
        }

        public Builder withComplainId(int complainId) {
            this.complainId = complainId;
            return this;
        }

        public ComplainFilter build() {
            return new ComplainFilter(this);
        }
    }

    private ComplainFilter(Builder builder) {
        this.page = builder.page;
        this.pageSize = builder.pageSize;
        this.complainerUsername = Optional.ofNullable(builder.complainerUsername);
        this.moderatorUsername = Optional.ofNullable(builder.moderatorUsername);
        this.tradeId = Optional.ofNullable(builder.tradeId);
        this.complainStatus = Optional.ofNullable(builder.complainStatus);
        this.complainId = Optional.ofNullable(builder.complainId);
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Optional<String> getComplainerUsername() {
        return complainerUsername;
    }

    public Optional<ComplainStatus> getComplainStatus() {
        return complainStatus;
    }

    public Optional<String> getModeratorUsername() {
        return moderatorUsername;
    }

    public Optional<Integer> getTradeId() {
        return tradeId;
    }

    public Optional<Integer> getComplainId() {
        return complainId;
    }
}
