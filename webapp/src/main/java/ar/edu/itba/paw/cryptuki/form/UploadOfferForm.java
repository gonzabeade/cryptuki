package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UploadOfferForm {
    @Min(1)
    @NotNull
    private Integer maxAmount;

    @NotNull
    private float price;

    @NotNull
    private String cryptocurrency;

    @NotNull
    private String[] paymentMethods;



}
