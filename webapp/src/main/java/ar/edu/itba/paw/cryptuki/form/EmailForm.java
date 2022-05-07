package ar.edu.itba.paw.cryptuki.form;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmailForm {
    @Size(min=6, max= 100)
    @Pattern(regexp=".+@.+\\..+")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
