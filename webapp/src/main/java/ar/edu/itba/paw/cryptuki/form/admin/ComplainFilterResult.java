package ar.edu.itba.paw.cryptuki.form.admin;

import ar.edu.itba.paw.ComplainFilter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Optional;

public class ComplainFilterResult {


    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate fromDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    LocalDate toDate;

    @Min(0)
    Integer offerId;

    @Min(0)
    Integer tradeId;

    String complainerUsername;

    public Optional<LocalDate> getFromDate() {
        return Optional.ofNullable(fromDate);
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public Optional<LocalDate> getToDate() {
        return Optional.ofNullable(toDate);
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public Optional<Integer> getOfferId() {
        return Optional.ofNullable(offerId);
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public Optional<Integer> getTradeId() {
        return Optional.ofNullable(tradeId);
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public Optional<String> getComplainerUsername() {
        return Optional.ofNullable(complainerUsername);
    }

    public void setComplainerUsername(String complainerUsername) {
        this.complainerUsername = complainerUsername;
    }

    public ComplainFilter.Builder toComplainFilterBuilder() {
        return new ComplainFilter.Builder()
                .withComplainerUsername(complainerUsername)
                .withTradeId(tradeId)
                .withOfferId(offerId)
                .from(fromDate)
                .to(toDate);
    }
}
