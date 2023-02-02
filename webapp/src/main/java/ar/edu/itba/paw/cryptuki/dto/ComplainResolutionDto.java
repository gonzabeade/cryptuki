package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainStatus;
import ar.edu.itba.paw.model.ComplaintResolution;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDateTime;

public class ComplainResolutionDto {

    private ComplaintResolution resolution;
    private String comments;
    private URI self;
    private URI moderator;

    public static ComplainResolutionDto fromComplain(final Complain complain, final UriInfo uriInfo) {
        final ComplainResolutionDto dto = new ComplainResolutionDto();

        dto.comments = complain.getModeratorComments().orElse("");

        UriBuilder complaintUriBuilder = uriInfo.getBaseUriBuilder()
                .path("/api/complaints")
                .path(String.valueOf(complain.getComplainId()));

        dto.self = complaintUriBuilder.path("resolution").build();

        dto.moderator = uriInfo.getBaseUriBuilder()
                .path("/api/users")
                .path(complain.getModerator().get().getUsername().get())
                .build();

        dto.resolution = complain.getResolution();

        return dto;
    }

    public ComplaintResolution getResolution() {
        return resolution;
    }

    public void setResolution(ComplaintResolution resolution) {
        this.resolution = resolution;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getModerator() {
        return moderator;
    }

    public void setModerator(URI moderator) {
        this.moderator = moderator;
    }
}
