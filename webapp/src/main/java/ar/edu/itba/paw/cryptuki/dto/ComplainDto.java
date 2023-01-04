package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainStatus;

import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ComplainDto {

    private int complainId;
    private ComplainStatus complainStatus;
    private String comments;
    private LocalDateTime date;

    private URI self;
    private URI trade;
    private URI complainer;
    private URI resolution;

    public static ComplainDto fromComplain(final Complain complain, final UriInfo uriInfo) {
        final ComplainDto dto = new ComplainDto();

        dto.comments = complain.getComplainerComments().orElse("");
        dto.complainStatus = complain.getStatus();
        dto.date = complain.getDate();
        dto.complainId = complain.getComplainId();

        UriBuilder complainUriBuilder = uriInfo.getBaseUriBuilder()
                .path("/api/complaints")
                .path(String.valueOf(complain.getComplainId()));

        dto.self = complainUriBuilder.build();

        dto.resolution = complainUriBuilder.path("resolution").build();

        dto.complainer = uriInfo.getBaseUriBuilder()
                .path("/api/users")
                .path(complain.getComplainer().getUsername().get())
                .build();

        dto.trade = uriInfo.getBaseUriBuilder()
                .path("/api/trades")
                .path(String.valueOf(complain.getTrade().getTradeId()))
                .build();

        return dto;
    }

    public int getComplainId() {
        return complainId;
    }

    public void setComplainId(int complainId) {
        this.complainId = complainId;
    }

    public ComplainStatus getComplainStatus() {
        return complainStatus;
    }

    public void setComplainStatus(ComplainStatus complainStatus) {
        this.complainStatus = complainStatus;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getTrade() {
        return trade;
    }

    public void setTrade(URI trade) {
        this.trade = trade;
    }

    public URI getComplainer() {
        return complainer;
    }

    public void setComplainer(URI complainer) {
        this.complainer = complainer;
    }

    public URI getResolution() {
        return resolution;
    }

    public void setResolution(URI resolution) {
        this.resolution = resolution;
    }
}
