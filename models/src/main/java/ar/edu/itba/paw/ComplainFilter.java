package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.ComplainStatus;


import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalInt;


// TODO! Return optionals
public class ComplainFilter {

    private final int page;
    private final int pageSize;
    private final String complainerUsername;
    private final ComplainStatus complainStatus;
    private final String moderatorUsername;
    private final Integer tradeId;

    private final LocalDate from;

    private final LocalDate to;

    private final Integer complainId;


    public static final class Builder {
        private int page = 0;
        private int pageSize = 1;
        private String complainerUsername;
        private ComplainStatus complainStatus;
        private String moderatorUsername;
        private Integer tradeId;

        private LocalDate from;
        private LocalDate to;

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

        public Builder from(LocalDate from) {
            this.from = from;
            return this;
        }

        public Builder to(LocalDate to) {
            this.to = to;
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
        this.from = builder.from;
        this.to = builder.to;
    }

    public int getPage() {
        return page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public Optional<String> getComplainerUsername() {
        return Optional.ofNullable(complainerUsername);
    }

    public Optional<ComplainStatus> getComplainStatus() {
        return Optional.ofNullable(complainStatus);
    }

    public Optional<String> getModeratorUsername() {
        return Optional.ofNullable(moderatorUsername);
    }

    public OptionalInt getTradeId() {
        return tradeId == null ? OptionalInt.empty() : OptionalInt.of(tradeId);
    }

    public OptionalInt getComplainId() {
        return complainId == null ? OptionalInt.empty() : OptionalInt.of(complainId);
    }

    public Optional<LocalDate> getFrom() {
        return Optional.ofNullable(from);
    }

    public Optional<LocalDate> getTo() {
        return Optional.ofNullable(to);
    }
}
