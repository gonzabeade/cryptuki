package ar.edu.itba.paw.cryptuki.form.legacy;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.cryptuki.form.OfferDataForm;
import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.OfferStatus;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class ModifyOfferForm extends OfferDataForm {

    @Size(min = 1, max = 10)
    @NotNull
    @ValueOfEnum(enumClass = OfferStatus.class)
    private String offerStatus;

    public String getOfferStatus() {
        return offerStatus;
    }

    public void setOfferStatus(String offerStatus) {
        this.offerStatus = offerStatus;
    }

    @Override
    public OfferPO toOfferParameterObject() {
        return super.toOfferParameterObject().withStatus(OfferStatus.valueOf(offerStatus));
    }

    @Override
    public void fillFromOffer(Offer offer) {
        super.fillFromOffer(offer);
        setOfferStatus(offer.getOfferStatus().toString());
    }

}

