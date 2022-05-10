package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.cryptuki.form.annotation.MinMax;
import ar.edu.itba.paw.persistence.Offer;
import ar.edu.itba.paw.persistence.PaymentMethod;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Arrays;
import java.util.stream.Collectors;

@MinMax
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
        OfferDigest.Builder builder = new OfferDigest.Builder(sellerId, getCryptocurrency(), getPrice()).withMinQuantity(getMinAmount()).withMaxQuantity(getMaxAmount()).withComments(getMessage()).withId(offerId);
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
        setPaymentMethods(
                offer.getPaymentMethods().stream().map(pm -> pm.getName()).toArray(String[]::new)
        );
    }

}

