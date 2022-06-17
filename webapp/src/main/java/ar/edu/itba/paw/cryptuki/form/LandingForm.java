package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class LandingForm {
    private Collection<String> coins;
    @Min(0)
    private Integer page = 0;
    @Min(0)
    @Max(3)
    private Integer orderCriteria = 2;

    private Collection<String> location;

    private Double arsAmount;

    public Double getArsAmount() {
        return arsAmount;
    }

    public void setArsAmount(Double arsAmount) {
        this.arsAmount = arsAmount;
    }

    public Collection<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> locations) {
        this.location = locations;
    }

    public Collection<String> getCoins() {
        return coins;
    }

    public void setCoins(String[] coins) {
        this.coins = Arrays.asList(coins);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getOrderCriteria() {
        return orderCriteria;
    }

    public void setOrderCriteria(Integer orderCriteria) {
        this.orderCriteria = orderCriteria;
    }
}
