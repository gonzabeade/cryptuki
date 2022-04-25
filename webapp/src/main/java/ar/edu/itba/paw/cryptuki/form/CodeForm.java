package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CodeForm {

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
}
