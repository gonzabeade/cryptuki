package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;


public class KycApprovalForm {
   @NotNull
   private Boolean approved;

   private String message;

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
