package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.Offer;

import java.util.*;

public class OfferFilter {
    private final Collection<String> paymentMethods = new LinkedList<>();
    private final Collection<String> cryptoCodes = new LinkedList<>();
    private final Collection<Integer> ids = new LinkedList<>();

    private int page = 0;
    private int pageSize = 1;

    private Double minPrice;
    private Double maxPrice;

    private String username;

    private Collection<String> status = new LinkedList<>();

    private OfferOrderCriteria orderCriteria = OfferOrderCriteria.DATE;
    private OfferOrderDirection orderDirection = OfferOrderDirection.DESC;


    public Collection<String> getPaymentMethods() {
        return Collections.unmodifiableCollection(paymentMethods);
    }
    public Collection<String> getCryptoCodes() {
        return Collections.unmodifiableCollection(cryptoCodes);
    }
    public Collection<Integer> getIds() {
        return Collections.unmodifiableCollection(ids);
    }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }
    public OptionalDouble getMaxPrice() { return maxPrice == null ? OptionalDouble.empty() : OptionalDouble.of(maxPrice); }
    public OptionalDouble getMinPrice() { return minPrice == null ? OptionalDouble.empty() : OptionalDouble.of(minPrice); }
    public Optional<String> getUsername() { return Optional.ofNullable(username); }
    public Collection<String> getStatus() {
        return Collections.unmodifiableCollection(status);
    }
    public OfferOrderDirection getOrderDirection() {
        return orderDirection;
    }
    public OfferOrderCriteria getOrderCriteria() {
        return orderCriteria;
    }

    public OfferFilter byStatus(String status) {
        if ( status != null)
            this.status.add(status);
        return this;
    }

    public OfferFilter byCryptoCode(String cryptoCode) {
        if ( cryptoCode != null)
            this.cryptoCodes.add(cryptoCode);
        return this;
    }

    public OfferFilter byPaymentMethod(String paymentMethod) {
        if ( paymentMethod != null)
            this.paymentMethods.add(paymentMethod);
        return this;
    }
    public OfferFilter byOfferId(Integer id) {
        if ( id != null)
            this.ids.add(id);
        return this;
    }

    public OfferFilter byMinPrice(Double minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public OfferFilter byUsername(String uname) {
        this.username = uname;
        return this;
    }

    public OfferFilter byMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
        return this;
    }

    public OfferFilter fromPage(int page) {
        this.page = page;
        return this;
    }
    public OfferFilter withPageSize(int page) {
        this.pageSize = page;
        return this;
    }

    public OfferFilter withOrderingCriterion(int criterion){
        this.orderCriteria = OfferOrderCriteria.values()[criterion];
        return this;
    }

    public OfferFilter withOrderingDirection(int direction){
        this.orderDirection = OfferOrderDirection.values()[direction];
        return this;
    }

}
