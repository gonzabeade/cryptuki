package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.*;

import javax.ws.rs.core.UriInfo;
import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Locale;

public class UserDto {


    private int id;
    private String username;
    private int ratingCount;
    private double rating;
    private LocalDateTime lastLogin;
    private Locale locale;

    private URI complains;
    private URI kyc;
    private URI offers;
    private URI self;

    public static UserDto fromUser(User user, UriInfo uriInfo) {
        UserAuth auth = user.getUserAuth();
        UserDto dto = new UserDto();

        dto.id = user.getId();
        dto.ratingCount = user.getRatingCount();
        dto.rating = user.getRating();
        dto.lastLogin = user.getLastLogin();
        dto.username = auth.getUsername();
        dto.locale = user.getLocale();

        dto.self = uriInfo.getAbsolutePathBuilder()
                .replacePath("users")
                .path(dto.username)
                .build();

        dto.kyc = uriInfo.getAbsolutePathBuilder()
                .replacePath("kyc")
                .path(dto.username)
                .build();

        dto.complains = uriInfo.getAbsolutePathBuilder()
                .replacePath("complains")
                .queryParam("fromUser", dto.username)
                .build();

        dto.offers = uriInfo.getAbsolutePathBuilder()
                .replacePath("offers")
                .queryParam("fromUser", dto.username)
                .build();

        return dto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public URI getComplains() {
        return complains;
    }

    public void setComplains(URI complains) {
        this.complains = complains;
    }

    public URI getKyc() {
        return kyc;
    }

    public void setKyc(URI kyc) {
        this.kyc = kyc;
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
}
