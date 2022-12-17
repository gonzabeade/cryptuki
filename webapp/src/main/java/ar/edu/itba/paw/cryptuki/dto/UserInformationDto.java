package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.model.User;
import ar.edu.itba.paw.model.UserAuth;
import ar.edu.itba.paw.model.UserStatus;

import javax.ws.rs.core.UriInfo;

public class UserInformationDto {

    private String email;
    private String phoneNumber;
    private UserStatus status;

    public static UserInformationDto fromUser(User user, UriInfo uriInfo) {
        UserAuth auth = user.getUserAuth();
        UserInformationDto dto = new UserInformationDto();

        dto.email = user.getEmail();
        dto.phoneNumber = user.getPhoneNumber();
        dto.status = auth.getUserStatus();

        return dto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

}
