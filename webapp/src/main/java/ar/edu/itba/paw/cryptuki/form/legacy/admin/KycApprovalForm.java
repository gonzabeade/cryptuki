package ar.edu.itba.paw.cryptuki.form.legacy.admin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


public class KycApprovalForm {

    @Size(min=1, max=140)
    @NotNull
    private String message;

    @Size(min=1, max=140)
    @NotNull
    private String username;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
