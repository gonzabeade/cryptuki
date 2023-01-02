package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.*;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class UserDto {

    private int userId;
    private String username;
    private int ratingCount;
    private double rating;
    private LocalDateTime lastLogin;
    private Locale locale;

    private URI complaints;
    private URI kycInformation;
    private URI offers;
    private URI self;
    private URI contactInformation;


    public static UserDto fromUser(User user, UriInfo uriInfo) {
        UserAuth auth = user.getUserAuth();
        UserDto dto = new UserDto();

        dto.userId = user.getId();
        dto.ratingCount = user.getRatingCount();
        dto.rating = user.getRating();
        dto.lastLogin = user.getLastLogin();
        dto.username = auth.getUsername();
        dto.locale = user.getLocale();

        dto.self = uriInfo.getBaseUriBuilder()
                .path("api/users")
                .path(dto.username)
                .build();

        dto.kycInformation = uriInfo.getBaseUriBuilder()
                .path("api/users")
                .path(dto.username)
                .path("kyc")
                .build();


        dto.complaints = uriInfo.getBaseUriBuilder()
                .path("api/complaints")
                .queryParam("from_user", dto.username)
                .build();

        dto.offers = uriInfo.getBaseUriBuilder()
                .path("api/offers")
                .queryParam("from_user", dto.username)
                .build();

        return dto;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(int ratingCount) {
        this.ratingCount = ratingCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public LocalDateTime getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(LocalDateTime lastLogin) {
        this.lastLogin = lastLogin;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public URI getComplaints() {
        return complaints;
    }

    public void setComplaints(URI complains) {
        this.complaints = complains;
    }

    public URI getKycInformation() {
        return kycInformation;
    }

    public void setKycInformation(URI kycInformation) {
        this.kycInformation = kycInformation;
    }

    public URI getOffers() {
        return offers;
    }

    public void setOffers(URI offers) {
        this.offers = offers;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }

    public URI getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(URI contactInformation) {
        this.contactInformation = contactInformation;
    }
}
