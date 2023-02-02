package ar.edu.itba.paw.cryptuki.form.legacy.auth;

import ar.edu.itba.paw.cryptuki.annotation.validation.CodeCorrect;
import ar.edu.itba.paw.cryptuki.annotation.validation.UsernameRegistered;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@CodeCorrect(
        codeField="code",
        usernameField="username"
)
public class RecoverPasswordForm {
    @NotNull
    private Integer code;
    @Size(min = 1)
    @UsernameRegistered
    private String username;
    @Size(min = 6, max = 100)
    private String password;
    @Size(min = 6, max = 100)
    private String repeatPassword;

    public Integer getCode() {
        return code;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getRepeatPassword() {
        return repeatPassword;
    }


    public void setCode(Integer code) {
        this.code = code;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }
}
