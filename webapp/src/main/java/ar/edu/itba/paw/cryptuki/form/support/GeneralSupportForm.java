package ar.edu.itba.paw.cryptuki.form.support;

import org.hibernate.validator.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class GeneralSupportForm {

    @Size(min=6, max= 100)
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @NotEmpty
    @Size(min=1, max= 140)
    private String message;


    public String getEmail() {
        return email;
    }
    public String getMessage() {
        return message;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
