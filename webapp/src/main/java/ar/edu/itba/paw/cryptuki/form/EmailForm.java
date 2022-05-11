package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.form.annotation.EmailRegistered;
import ar.edu.itba.paw.cryptuki.form.annotation.EmailVerified;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class EmailForm {
    @Size(min=6, max= 100)
    @Pattern(regexp=".+@.+\\..+")
    @EmailRegistered
    @EmailVerified
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
