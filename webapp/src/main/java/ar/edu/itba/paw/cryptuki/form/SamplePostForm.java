package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public class SamplePostForm {

    @NotNull
    @NotEmpty
    private String sellerId;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }
}

