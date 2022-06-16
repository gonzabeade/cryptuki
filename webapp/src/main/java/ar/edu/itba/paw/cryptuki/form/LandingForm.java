package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

public class LandingForm {
    private String[] coins;
    @Min(0)
    private Integer page = 0;
    @Min(0)
    @Max(3)
    private Integer orderCriteria = 2;

    private List<String> location;

    private double arsAmount;

    public double getArsAmount() {
        return arsAmount;
    }

    public void setArsAmount(double arsAmount) {
        this.arsAmount = arsAmount;
    }

    public List<String> getLocation() {
        return location;
    }

    public void setLocation(List<String> locations) {
        this.location = locations;
    }

    public String[] getCoins() {
        return coins;
    }

    public void setCoins(String[] coins) {
        this.coins = coins;
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
