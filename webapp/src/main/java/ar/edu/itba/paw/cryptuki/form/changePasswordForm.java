package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class changePasswordForm {

    @NotNull
    @Size(min = 6, max = 100)
    private String oldPassword;

    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String repeatPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
