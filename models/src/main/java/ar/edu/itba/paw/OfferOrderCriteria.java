package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.Offer;

import java.util.Comparator;

public enum OfferOrderCriteria {

    PRICE( (o1, o2) -> (int) (o2.getAskingPrice() - o2.getAskingPrice())),
    LAST_LOGIN( (o1, o2) -> o2.getSeller().getLastLogin().compareTo(o1.getSeller().getLastLogin())),
    DATE(Comparator.comparing(Offer::getDate)),
    RATE( (o1, o2)-> (int) (o2.getSeller().getRating() - o1.getSeller().getRating()));

    Comparator<Offer> criteria;

    OfferOrderCriteria(Comparator<Offer> criteria) {
        this.criteria = criteria;
    }

    public Comparator<Offer> getCriteria() {
        return criteria;
    }
}
