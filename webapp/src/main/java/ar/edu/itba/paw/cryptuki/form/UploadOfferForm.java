package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.model.Offer;
import ar.edu.itba.paw.model.parameterObject.OfferPO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UploadOfferForm extends OfferDataForm{

    @Size(min=2, max=30)
    @NotNull
    private String cryptoCode;

    public String getCryptoCode() {
        return cryptoCode;
    }

    public void setCryptoCode(String cryptoCode) {
        this.cryptoCode = cryptoCode;
    }

    @Override
    public OfferPO toOfferParameterObject() {
        return super.toOfferParameterObject().withCryptoCode(cryptoCode);
    }

    public void fillFromOffer(Offer offer) {
        super.fillFromOffer(offer);
        setCryptoCode(offer.getCrypto().getCode());
    }
}
