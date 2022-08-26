package ar.edu.itba.paw.cryptuki.form.legacy.seller;

import ar.edu.itba.paw.cryptuki.annotation.MultipartSizeCheck;
import ar.edu.itba.paw.model.IdType;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;
import ar.edu.itba.paw.cryptuki.annotation.MultipartCheck;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.IOException;


public class KycForm {

    @Size(min=1, max=140)
    @NotNull
    private String givenNames;

    @Size(min=1, max=140)
    @NotNull
    private String surnames;

    @Size(min=1, max=140)
    @NotNull
    private String username;

    @Size(min=1, max=140)
    @NotNull
    private String emissionCountry;

    @Size(min=1, max=140)
    @NotNull
    private String idCode;

    @Size(min=1, max=140)
    @NotNull
    private String idType;

    @MultipartCheck
    @MultipartSizeCheck(maxSize = (1<<21))
    private MultipartFile idPhoto;

    @MultipartCheck
    @MultipartSizeCheck(maxSize = (1<<21))
    private MultipartFile validationPhoto;

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

    public String getIdType() {
        return idType;
    }

    public void setIdType(String idType) {
        this.idType = idType; // throws IllegalArgumentException, translated into bad request
    }

    public MultipartFile getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(MultipartFile idPhoto) {
        this.idPhoto = idPhoto;
    }

    public MultipartFile getValidationPhoto() {
        return validationPhoto;
    }

    public void setValidationPhoto(MultipartFile validationPhoto) {
        this.validationPhoto = validationPhoto;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    public KycInformationPO toParameterObject() throws IOException {
        return new KycInformationPO(username, givenNames, surnames)
                .withIdCode(idCode)
                .withIdPhoto(idPhoto.getBytes())
                .withIdPhotoType(idPhoto.getContentType())
                .withIdType(IdType.valueOf(idType))
                .withEmissionCountry(emissionCountry)
                .withValidationPhoto(validationPhoto.getBytes())
                .withValidationPhotoType(validationPhoto.getContentType());
    }


}

