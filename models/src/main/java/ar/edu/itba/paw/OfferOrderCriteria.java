package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.Offer;

import java.util.Comparator;

public enum OfferOrderCriteria {

    PRICE("o.askingPrice"),
    LAST_LOGIN("o.seller.lastLogin"),
    DATE("o.date"),
    RATE("o.seller.ratingSum");

    private final String path;

    OfferOrderCriteria(String path){
        this.path = path;
    }

    public String getPath(){
        return path;
    }

}
