package ar.edu.itba.paw.persistence;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class OfferFilter { // TODO: should be an interface?

    private boolean closed = Boolean.FALSE;
    private final Collection<String> paymentMethods = new LinkedList<>();
    private final Collection<String> cryptoCodes = new LinkedList<>();
    private final Collection<Float>  quantities = new LinkedList<>(); // Todo -- Should be Collection<String> ?
    private final Collection<Integer> ids = new LinkedList<>();

    private int page = 0;
    private int pageSize = 1;


    // Using the Optional<String> solves the defensive copy problem too
    public Collection<String> getPaymentMethods() {
        return Collections.unmodifiableCollection(paymentMethods);
    }
    public Collection<String> getCryptoCodes() {
        return Collections.unmodifiableCollection(cryptoCodes);
    }
    public Collection<Float> getQuantities() {
        return Collections.unmodifiableCollection(quantities);
    }
    public Collection<Integer> getIds() {
        return Collections.unmodifiableCollection(ids);
    }
    public int getPage() { return page; }
    public int getPageSize() { return pageSize; }

    public OfferFilter byCryptoCode(String cryptoCode) {
        if (!closed) this.cryptoCodes.add(cryptoCode);
        return this;
    }
    public OfferFilter byQuantity(float quantity) {
        if (!closed) this.quantities.add(quantity);
        return this;
    }
    public OfferFilter byPaymentMethod(String paymentMethod) {
        if (!closed) this.paymentMethods.add(paymentMethod);
        return this;
    }
    public OfferFilter byOfferId(int id) {
        if (!closed) this.ids.add(id);
        return this;
    }

    public OfferFilter fromPage(int page) {
        if (!closed) this.page = page;
        return this;
    }
    public OfferFilter withPageSize(int page) {
        if (!closed) this.pageSize = page;
        return this;
    }

    public void close() {
        this.closed = Boolean.TRUE;
    }
}
