package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CodeForm {

    @NotNull
    private Integer code;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
