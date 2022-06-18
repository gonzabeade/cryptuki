package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.model.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LandingForm {

    private Collection<String> coins;
    private Collection<String> location;
    @Min(0)
    private Integer page = 0;
    @Min(0)
    @Max(3)
    @NotNull
    private Integer orderCriteria = 2;
    private Double arsAmount;


    public Double getArsAmount() {
        return arsAmount;
    }
    public Collection<String> getLocation() {
        return location;
    }
    public Collection<String> getCoins() {
        return coins;
    }
    public Integer getPage() {
        return page;
    }
    public Integer getOrderCriteria() {
        return orderCriteria;
    }



    public void setArsAmount(Double arsAmount) {
        this.arsAmount = arsAmount;
    }
    public void setLocation(List<String> locations) {
        this.location = locations;
    }
    public void setCoins(String[] coins) {
        this.coins = Arrays.asList(coins);
    }
    public void setPage(Integer page) {
        this.page = page;
    }
    public void setOrderCriteria(Integer orderCriteria) {
        this.orderCriteria = orderCriteria;
    }

    public OfferFilter toOfferFilter() {
        return new OfferFilter()
                .orderingBy(OfferOrderCriteria.values()[orderCriteria])
                .withCryptoCodes(coins)
                .withLocations(location)
                .withIsQuantityInRange(arsAmount)
                .withPage(page);
    }
}
