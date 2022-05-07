package ar.edu.itba.paw.cryptuki.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SolveComplaintForm {
    @Size(min=1)
    private String comments;

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }
}
