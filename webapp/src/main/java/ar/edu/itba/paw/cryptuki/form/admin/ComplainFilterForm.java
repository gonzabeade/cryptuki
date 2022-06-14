package ar.edu.itba.paw.cryptuki.form.admin;

import ar.edu.itba.paw.model.ComplainFilter;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.model.Country;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.util.Optional;

public class ComplainFilterForm {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate toDate;
    @Min(0)
    private Integer offerId;
    @Min(0)
    private Integer tradeId;
    private String complainer;

    public Optional<LocalDate> getFromDate() {
        return Optional.ofNullable(fromDate);
    }
    public Optional<String> getComplainer() {
        return Optional.ofNullable(complainer);
    }
    public Optional<LocalDate> getToDate() {
        return Optional.ofNullable(toDate);
    }
    public Optional<Integer> getOfferId() {
        return Optional.ofNullable(offerId);
    }
    public Optional<Integer> getTradeId() {
        return Optional.ofNullable(tradeId);
    }


    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }
    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
    public void setOfferId(Integer offerId) {
        this.offerId = offerId;
    }
    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
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
