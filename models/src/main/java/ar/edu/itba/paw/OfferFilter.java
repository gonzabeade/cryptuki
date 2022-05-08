package ar.edu.itba.paw;
import ar.edu.itba.paw.persistence.Offer;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class OfferFilter {
    private final Collection<String> paymentMethods = new LinkedList<>();
    private final Collection<String> cryptoCodes = new LinkedList<>();
    private final Collection<Integer> ids = new LinkedList<>();

    private int page = 0;
    private int pageSize = 1;

    private float minPrice = 0;
    private float maxPrice = Float.MAX_VALUE;

    private String username = null;

    private Collection<String> status = new LinkedList<>();



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
    public float getMaxPrice() { return maxPrice; }
    public float getMinPrice() { return minPrice; }
    public String getUsername() { return username; }
    public Collection<String> getStatus() {
        return Collections.unmodifiableCollection(status);
    }

    public OfferFilter byStatus(String status) {
        this.status.add(status);
        return this;
    }

    public OfferFilter byCryptoCode(String cryptoCode) {
        this.cryptoCodes.add(cryptoCode);
        return this;
    }

    public OfferFilter byPaymentMethod(String paymentMethod) {
        this.paymentMethods.add(paymentMethod);
        return this;
    }
    public OfferFilter byOfferId(int id) {
        this.ids.add(id);
        return this;
    }

    public OfferFilter byMinPrice(float minPrice) {
        this.minPrice = minPrice;
        return this;
    }

    public OfferFilter byUsername(String uname) {
        this.username = uname;
        return this;
    }

    public OfferFilter byMaxPrice(float maxPrice) {
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
}
