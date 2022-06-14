package ar.edu.itba.paw.cryptuki.form.auth;

import ar.edu.itba.paw.cryptuki.form.annotation.CodeCorrect;
import ar.edu.itba.paw.cryptuki.form.annotation.MinLessThanMax;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@CodeCorrect(
        codeField="code",
        usernameField="username"
)
public class CodeForm {

    @NotNull
    private Integer code;
    @NotNull
    @Size(min=1)
    private String username;

    public Integer getCode() {
        return code;
    }
    public String getUsername() {
        return username;
    }


    public void setCode(Integer code) {
        this.code = code;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
