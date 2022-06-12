package ar.edu.itba.paw.cryptuki.form.admin;

import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.model.Country;
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

    String complainer;

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

    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }

    public Optional<Integer> getTradeId() {
        return Optional.ofNullable(tradeId);
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public Optional<String> getComplainer() {
        return Optional.ofNullable(complainer);
    }

    public void setComplainer(String complainer) {
        this.complainer= complainer;
    }

    public ComplainFilter toComplainFilter() {
        return new ComplainFilter()
                .restrictedToComplainerUsername(complainer)
                .restrictedToTradeId(tradeId)
                .restrictedToOfferId(offerId)
                .from(fromDate)
                .to(toDate);
    }
}
