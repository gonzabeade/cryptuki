package ar.edu.itba.paw.cryptuki.form.auth;

import ar.edu.itba.paw.cryptuki.form.annotation.EqualFields;

import javax.validation.constraints.Size;

@EqualFields(
        field1="password",
        field2="repeatPassword"
)
public class ChangePasswordForm {

    @Size(min = 6, max = 100)
    private String password;
    @Size(min = 6, max = 100)
    private String repeatPassword;


    public String getPassword() {
        return password;
    }
    public String getRepeatPassword() {
        return repeatPassword;
    }


    public void setPassword(String password) {
        this.password = password;
    }
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
