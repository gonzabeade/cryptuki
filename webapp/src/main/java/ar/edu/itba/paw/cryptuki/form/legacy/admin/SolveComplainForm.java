package ar.edu.itba.paw.cryptuki.form.legacy.admin;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SolveComplainForm {
    @Size(min=1)
    private String comments;

    @NotNull
    private String result;
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
