package ar.edu.itba.paw.cryptuki.form;

import ar.edu.itba.paw.cryptuki.annotation.DuplicateEmail;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class ChangePasswordForm {

    @Size(min = 6, max = 100)
    private String password;
    private int code;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
       this.password = password;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
