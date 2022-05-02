package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.ComplainStatus;


import java.util.Optional;

public class ComplainFilter {

    private final int page;
    private final int pageSize;
    private final String complainerUsername;
    private final ComplainStatus complainStatus;
    private final String moderatorUsername;
    private final Integer tradeId;

    private final Integer complainId;


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
        this.complainerUsername = builder.complainerUsername;
        this.moderatorUsername = builder.moderatorUsername;
        this.tradeId = builder.tradeId;
        this.complainStatus = builder.complainStatus;
        this.complainId = builder.complainId;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public String getComplainerUsername() {
        return complainerUsername;
    }

    public ComplainStatus getComplainStatus() {
        return complainStatus;
    }

    public String getModeratorUsername() {
        return moderatorUsername;
    }

    public Integer getTradeId() {
        return tradeId;
    }

    public Integer getComplainId() {
        return complainId;
    }
}
