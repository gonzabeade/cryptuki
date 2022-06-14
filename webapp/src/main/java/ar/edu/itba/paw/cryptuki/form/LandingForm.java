package ar.edu.itba.paw.cryptuki.form;

public class LandingForm {
    private String[] coins;
    private Integer page = 0;
    private Integer orderCriteria = 0;

    private String[] location;

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] locations) {
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
