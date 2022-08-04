package ar.edu.itba.paw.cryptuki.form.legacy.auth;

import ar.edu.itba.paw.cryptuki.form.legacy.annotation.EmailRegistered;
import ar.edu.itba.paw.cryptuki.form.legacy.annotation.EmailVerified;

import javax.validation.constraints.Pattern;

public class EmailForm {
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
