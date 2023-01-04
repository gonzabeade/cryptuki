package ar.edu.itba.paw.cryptuki.form.legacy.auth;

import ar.edu.itba.paw.cryptuki.annotation.validation.EqualFields;

import javax.validation.constraints.Size;

public class ChangePasswordForm {
    @Size(min = 6, max = 100)
    private String password;
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
