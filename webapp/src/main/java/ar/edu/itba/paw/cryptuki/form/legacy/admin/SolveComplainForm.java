package ar.edu.itba.paw.cryptuki.form.legacy.admin;

import ar.edu.itba.paw.cryptuki.annotation.validation.ValueOfEnum;
import ar.edu.itba.paw.model.ComplaintResolution;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SolveComplainForm {
    @Size(min=1)
    private String comments;

    @NotNull
    @ValueOfEnum(enumClass = ComplaintResolution.class)
    private String resolution;
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getResolution() {
        return resolution;
    }

    public void setResolution(String resolution) {
        this.resolution = resolution;
    }
}
