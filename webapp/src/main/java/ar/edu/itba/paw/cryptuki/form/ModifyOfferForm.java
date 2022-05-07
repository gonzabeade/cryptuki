package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.OfferDigest;
import ar.edu.itba.paw.cryptuki.form.annotation.MinMax;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

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


}

