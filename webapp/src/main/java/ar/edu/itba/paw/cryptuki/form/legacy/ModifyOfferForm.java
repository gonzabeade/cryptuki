package ar.edu.itba.paw.cryptuki.form.legacy.seller;

import ar.edu.itba.paw.cryptuki.form.UploadOfferForm;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import javax.validation.constraints.NotNull;


public class ModifyOfferForm extends UploadOfferForm {

    @NotNull
    private int offerId;

    public int getOfferId() {
        return offerId;
    }
    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    @Override
    public OfferPO toOfferParameterObject() {
        return super.toOfferParameterObject().withOfferId(offerId);
    }

    @Override
    public void fillFromOffer(Offer offer) {
        super.fillFromOffer(offer);
        setOfferId(offer.getOfferId());
    }

}

