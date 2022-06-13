package ar.edu.itba.paw.cryptuki.form.admin;

import javax.validation.constraints.Size;

public class SolveComplainForm {
    @Size(min=1)
    private String comments;

    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }
}
