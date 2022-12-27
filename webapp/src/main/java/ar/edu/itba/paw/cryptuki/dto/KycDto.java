package ar.edu.itba.paw.cryptuki.dto;

import ar.edu.itba.paw.cryptuki.controller.UserController;
import ar.edu.itba.paw.model.IdType;
import ar.edu.itba.paw.model.KycInformation;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class KycDto {

    private String givenNames;
    private String surnames;

    private String emissionCountry;
    private String idCode;
    private IdType idType;

    private URI idPhoto;
    private URI validationPhoto;

    private URI user;
    private URI self;

    public static KycDto fromKycInformation(KycInformation kycInformation, UriInfo uriInfo) {

        final KycDto dto = new KycDto();
        dto.emissionCountry = kycInformation.getEmissionCountry();
        dto.givenNames = kycInformation.getGivenNames();
        dto.idCode = kycInformation.getIdCode();
        dto.surnames = kycInformation.getSurnames();
        dto.idType = kycInformation.getIdType();


        String username = kycInformation.getUser().getUsername().get();

        dto.self = uriInfo.getAbsolutePathBuilder().build();

        dto.validationPhoto = uriInfo.getAbsolutePathBuilder()
                .path("validationPhoto")
                .build();

        dto.idPhoto = uriInfo.getAbsolutePathBuilder()
                .path("idPhoto")
                .build();

        dto.user = uriInfo.getBaseUriBuilder()
                .path(UserController.class)
                .path(username)
                .build();

        return dto;
    }

    public String getGivenNames() {
        return givenNames;
    }

    public void setGivenNames(String givenNames) {
        this.givenNames = givenNames;
    }

    public String getSurnames() {
        return surnames;
    }

    public void setSurnames(String surnames) {
        this.surnames = surnames;
    }

    public String getEmissionCountry() {
        return emissionCountry;
    }

    public void setEmissionCountry(String emissionCountry) {
        this.emissionCountry = emissionCountry;
    }

    public String getIdCode() {
        return idCode;
    }

    public void setIdCode(String idCode) {
        this.idCode = idCode;
    }

    public IdType getIdType() {
        return idType;
    }

    public void setIdType(IdType idType) {
        this.idType = idType;
    }

    public URI getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(URI idPhoto) {
        this.idPhoto = idPhoto;
    }

    public URI getValidationPhoto() {
        return validationPhoto;
    }

    public void setValidationPhoto(URI validationPhoto) {
        this.validationPhoto = validationPhoto;
    }

    public URI getUser() {
        return user;
    }

    public void setUser(URI user) {
        this.user = user;
    }

    public URI getSelf() {
        return self;
    }

    public void setSelf(URI self) {
        this.self = self;
    }
}
