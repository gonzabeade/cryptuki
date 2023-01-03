package ar.edu.itba.paw.cryptuki.utils;

import ar.edu.itba.paw.cryptuki.annotation.validation.CollectionOfEnum;
import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.model.Location;
import ar.edu.itba.paw.model.OfferFilter;
import ar.edu.itba.paw.model.OfferOrderCriteria;
import ar.edu.itba.paw.model.OfferStatus;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.QueryParam;
import java.util.List;
import java.util.stream.Collectors;

public class OfferBeanParam {
    @QueryParam("page")
    @DefaultValue("0")
    private int page;
    @QueryParam("per_page")
    @DefaultValue("5")
    private int pageSize;
    @QueryParam("crypto_code")
    private List<String> cryptoCodes;
    @QueryParam("location")
    @CollectionOfEnum(enumClass = Location.class)
    private List<String> locations;
    @QueryParam("status")
    @CollectionOfEnum(
            enumClass = OfferStatus.class
    )
    private List<String> status;
    @QueryParam("exclude_user")
    private List<String> excludedUsernames;
    @QueryParam("by_user")
    private List<String> restrictedToUsernames;
    @QueryParam("order_by")
    @DefaultValue("DATE")
    @ValueOfEnum(enumClass = OfferOrderCriteria.class)
    private String orderCriteria;

    public OfferBeanParam() {
        // POJO
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public List<String> getCryptoCodes() {
        return cryptoCodes;
    }

    public void setCryptoCodes(List<String> cryptoCodes) {
        this.cryptoCodes = cryptoCodes;
    }

    public List<String> getLocations() {
        return locations;
    }

    public void setLocations(List<String> locations) {
        this.locations = locations;
    }

    public List<String> getStatus() {
        return status;
    }

    public void setStatus(List<String> status) {
        this.status = status;
    }

    public List<String> getExcludedUsernames() {
        return excludedUsernames;
    }

    public void setExcludedUsernames(List<String> excludedUsernames) {
        this.excludedUsernames = excludedUsernames;
    }

    public List<String> getRestrictedToUsernames() {
        return restrictedToUsernames;
    }

    public void setRestrictedToUsernames(List<String> restrictedToUsernames) {
        this.restrictedToUsernames = restrictedToUsernames;
    }

    public String getOrderCriteria() {
        return orderCriteria;
    }

    public void setOrderCriteria(String orderCriteria) {
        this.orderCriteria = orderCriteria;
    }

    public OfferFilter toOfferFilter() {
        OfferFilter filter = new OfferFilter()
                .excludeUsernames(getExcludedUsernames())
                .restrictedToUsernames(getRestrictedToUsernames())
                .withCryptoCodes(getCryptoCodes())
                .withLocations(getLocations())
                .withOfferStatus(getStatus().stream().map(o->OfferStatus.valueOf(o)).collect(Collectors.toList()))
                .orderingBy(OfferOrderCriteria.valueOf(getOrderCriteria()))
                .withPage(getPage())
                .withPageSize(getPageSize());
        return filter;
    }
}
