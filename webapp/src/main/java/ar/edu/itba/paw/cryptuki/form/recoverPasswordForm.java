package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class recoverPasswordForm {

    @NotNull
    private Integer code;

    private String username;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Size(min = 6, max = 100)
    private String password;

    @Size(min = 6, max = 100)
    private String repeatPassword;

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
