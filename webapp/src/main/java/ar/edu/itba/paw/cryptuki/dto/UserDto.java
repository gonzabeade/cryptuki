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

    private URI complains;
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


        dto.complains = uriInfo.getBaseUriBuilder()
                .path("api/complaints")
                .queryParam("from_user", dto.username)
                .build();

        dto.offers = uriInfo.getBaseUriBuilder()
                .path("api/offers")
                .queryParam("from_user", dto.username)
                .build();

        return dto;
    }


}
