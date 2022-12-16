package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.Complain;
import ar.edu.itba.paw.model.ComplainStatus;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class ComplainDto {

    private int complainId;
    private int tradeId;
    private int offerId;
    private ComplainStatus complainStatus;
    private String complainer;
    private String moderator;
    private String complainerEmail;
    private String complainerComments;
    private String moderatorComments;
    private LocalDateTime date;

    private URI selfUri;
    private URI tradeUri;
    private URI offerUri;
    private URI complainerUri;
    private URI moderatorUri;

    public static ComplainDto fromComplain(final Complain complain, final UriInfo uriInfo) {
        final ComplainDto dto = new ComplainDto();

        dto.setComplainId(complain.getComplainId());
        dto.setTradeId(complain.getTrade().getTradeId());
        dto.setOfferId(complain.getTrade().getOffer().getOfferId());
        dto.setComplainStatus(complain.getStatus());
        dto.setComplainer(complain.getComplainer().getUsername().get());
        dto.setComplainerEmail(complain.getComplainer().getEmail());
        dto.setComplainerComments(complain.getComplainerComments().get());
        if(complain.getModerator().isPresent()) {
            dto.setModerator(complain.getModerator().get().getUsername().get());
            dto.setModeratorComments(complain.getModeratorComments().get());
        }
        dto.setDate(complain.getDate());

        dto.setSelfUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("trades")
                        .path(String.valueOf(dto.getTradeId()))
                        .path("complains")
                        .path(String.valueOf(dto.getComplainId()))
                        .build()
        );

        dto.setTradeUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("trades")
                        .path(String.valueOf(dto.getTradeId()))
                        .build()
        );

        dto.setOfferUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("offers")
                        .path(String.valueOf(dto.getOfferId()))
                        .build()
        );

        dto.setComplainerUri(
                uriInfo.getAbsolutePathBuilder()
                        .replacePath("users")
                        .path(dto.getComplainer())
                        .build()
        );

        if(complain.getModerator().isPresent()) {
            dto.setModeratorUri(
                    uriInfo.getAbsolutePathBuilder()
                            .replacePath("users")
                            .path(dto.getModerator())
                            .build()
            );
        }

        return dto;

    }

    public int getComplainId() {
        return complainId;
    }

    public void setComplainId(int complainId) {
        this.complainId = complainId;
    }

    public int getTradeId() {
        return tradeId;
    }

    public void setTradeId(int tradeId) {
        this.tradeId = tradeId;
    }

    public int getOfferId() {
        return offerId;
    }

    public void setOfferId(int offerId) {
        this.offerId = offerId;
    }

    public ComplainStatus getComplainStatus() {
        return complainStatus;
    }

    public void setComplainStatus(ComplainStatus complainStatus) {
        this.complainStatus = complainStatus;
    }

    public String getComplainer() {
        return complainer;
    }

    public void setComplainer(String complainer) {
        this.complainer = complainer;
    }

    public String getModerator() {
        return moderator;
    }

    public void setModerator(String moderator) {
        this.moderator = moderator;
    }

    public String getComplainerEmail() {
        return complainerEmail;
    }

    public void setComplainerEmail(String complainerEmail) {
        this.complainerEmail= complainerEmail;
    }

    public String getComplainerComments() {
        return complainerComments;
    }

    public void setComplainerComments(String complainerComments) {
        this.complainerComments = complainerComments;
    }

    public String getModeratorComments() {
        return moderatorComments;
    }

    public void setModeratorComments(String moderatorComments) {
        this.moderatorComments = moderatorComments;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public URI getTradeUri() {
        return tradeUri;
    }

    public void setTradeUri(URI tradeUri) {
        this.tradeUri = tradeUri;
    }

    public URI getOfferUri() {
        return offerUri;
    }

    public void setOfferUri(URI offerUri) {
        this.offerUri = offerUri;
    }

    public URI getComplainerUri() {
        return complainerUri;
    }

    public void setComplainerUri(URI complainerUri) {
        this.complainerUri = complainerUri;
    }

    public URI getModeratorUri() {
        return moderatorUri;
    }

    public void setModeratorUri(URI moderatorUri) {
        this.moderatorUri = moderatorUri;
    }

    public URI getSelfUri() {
        return selfUri;
    }

    public void setSelfUri(URI selfUri) {
        this.selfUri = selfUri;
    }
}
