package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.cryptuki.form.annotation.MinLessThanMax;
import ar.edu.itba.paw.persistence.Offer;

import javax.validation.constraints.NotNull;

@MinLessThanMax(
        min="minAmount",
        max="maxAmount"
)
public class ModifyOfferForm extends UploadOfferForm {

    @NotNull
    private int offerId;

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public OfferDigest toOfferDigest(int sellerId) {
        OfferDigest.Builder builder = new OfferDigest.Builder(sellerId, getCryptocurrency(), getPrice()).withMinQuantity(getMinAmount()).withMaxQuantity(getMaxAmount()).withComments(getMessage()).withLocation(getLocation()).withId(offerId);
        for (String pm: getPaymentMethods())
                builder.withPaymentMethod(pm);
        return builder.build();
    }

    public void fillFromOffer(Offer offer) {
        setOfferId(offer.getId());
        setMinAmount(offer.getMinQuantity());
        setMaxAmount(offer.getMaxQuantity());
        setCryptocurrency(offer.getCrypto().getCode());
        setPrice(offer.getAskingPrice());
        setMessage(offer.getComments());
        setLocation(offer.getLocation());
        setPaymentMethods(
                offer.getPaymentMethods().stream().map(pm -> pm.getName()).toArray(String[]::new)
        );
    }

}

