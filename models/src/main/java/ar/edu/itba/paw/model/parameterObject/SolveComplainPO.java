package ar.edu.itba.paw.model.parameterObject;

import ar.edu.itba.paw.model.ComplaintResolution;

public class SolveComplainPO {

    private int complainId;

    private String moderatorUsername;

    private String comments;

    private String resolution;

    public int getComplainId() {
        return complainId;
    }

    public void setComplainId(int complainId) {
        this.complainId = complainId;
    }

    public String getModeratorUsername() {
        return moderatorUsername;
    }

    public void setModeratorUsername(String moderatorUsername) {
        this.moderatorUsername = moderatorUsername;
    }

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
