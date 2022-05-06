package ar.edu.itba.paw.cryptuki.form.admin;

import ar.edu.itba.paw.ComplainFilter;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public class ComplainFilterForm {


    LocalDate fromDate;
    LocalDate toDate;

    int offerId;
    int tradeId;

    String complainerUsername;

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public String getComplainerUsername() {
        return complainerUsername;
    }

    public void setComplainerUsername(String complainerUsername) {
        this.complainerUsername = complainerUsername;
    }

    public ComplainFilter.Builder toComplainFilterBuilder() {
        return new ComplainFilter.Builder()
                .withComplainerUsername(complainerUsername)
                .withTradeId(tradeId)
                .from(fromDate)
                .to(toDate);
    }
}
