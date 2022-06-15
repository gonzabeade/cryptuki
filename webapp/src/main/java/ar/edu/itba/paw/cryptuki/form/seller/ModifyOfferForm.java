package ar.edu.itba.paw.cryptuki.form.seller;

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

    public void fillFromOffer(Offer offer) {
        setOfferId(offer.getOfferId());
        setMinInCrypto(offer.getMinInCrypto());
        setMaxInCrypto(offer.getMaxInCrypto());
        setCryptoCode(offer.getCrypto().getCode());
        setUnitPrice((offer.getUnitPrice()));
        setFirstChat(offer.getComments());
        setLocation(offer.getLocation().toString());
    }

    @Override
    public OfferPO toOfferParameterObject() {
        return super.toOfferParameterObject().withOfferId(offerId);
    }

}

