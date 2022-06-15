package ar.edu.itba.paw.cryptuki.form.seller;

import java.util.Optional;

public class TradeFilterForm {
    private Integer page;
    private Integer focusOnOfferId;
    private Integer filterByOfferId;

    public Integer getFilterByOfferId() {
        return filterByOfferId;
    }
    public Integer getPage() {
        return page;
    }
    public Integer getFocusOnOfferId() {
        return focusOnOfferId;
    }

    public Optional<Integer> getPageOptional() {
        return Optional.ofNullable(page);
    }
    public Optional<Integer> getFocusOnOfferIdOptional() {
        return Optional.ofNullable(focusOnOfferId);
    }
    public Optional<Integer> getFilterByOfferIdOptional() {
        return Optional.ofNullable(filterByOfferId);
    }

    public void setPage(Integer page) {
        this.page = page;
    }
    public void setFocusOnOfferId(Integer focusOnOfferId) {
        this.focusOnOfferId = focusOnOfferId;
    }

    public void setFilterByOfferId(Integer filterByOfferId) {
        this.filterByOfferId = filterByOfferId;
    }
}
