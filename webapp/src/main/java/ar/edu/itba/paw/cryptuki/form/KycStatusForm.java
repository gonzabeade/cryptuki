package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.model.KycStatus;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class KycStatusForm {

    @NotNull
    @Size(min = 0, max = 240)
    private String comments;

    @NotNull
    @ValueOfEnum(enumClass = KycStatus.class)
    private String status;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
