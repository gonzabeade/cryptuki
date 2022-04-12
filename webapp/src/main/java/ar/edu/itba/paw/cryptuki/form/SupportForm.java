package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class SupportForm {
    @Size(min=6, max= 100)
    @Email()
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    @NotEmpty
    @Size(min=1, max= 140)
    private String message;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
