package ar.edu.itba.paw.parameterObject;

import ar.edu.itba.paw.model.IdType;
import ar.edu.itba.paw.model.KycInformation;

public class KycInformationPO {

    private final String username;
    private final String givenNames;
    private final String surnames;

    private String emissionCountry;
    private String idCode;
    private IdType idType;

    private byte[] idPhoto;
    private byte[] validationPhoto;

    private String idPhotoType;
    private String validationPhotoType;

    public KycInformationPO(String username, String givenNames, String surnames) {
        this.username = username;
        this.givenNames = givenNames;
        this.surnames = surnames;
    }

    public KycInformationPO withEmissionCountry(String emissionCountry) {
        this.emissionCountry = emissionCountry;
        return this;
    }


    public KycInformationPO withIdCode(String idCode) {
        this.idCode = idCode;
        return this;
    }

    public KycInformationPO withIdType(IdType idType) {
        this.idType = idType;
        return this;
    }

    public KycInformationPO withIdPhoto(byte[] idPhoto) {
        this.idPhoto = idPhoto;
        return this;
    }

    public KycInformationPO withValidationPhoto(byte[] validationPhoto) {
        this.validationPhoto = validationPhoto;
        return this;
    }

    public KycInformationPO withIdPhotoType(String idPhotoType) {
        this.idPhotoType = idPhotoType;
        return this;
    }

    public KycInformationPO withValidationPhotoType(String validationPhotoType) {
        this.validationPhotoType = validationPhotoType;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public String getGivenNames() {
        return givenNames;
    }

    public String getSurnames() {
        return surnames;
    }

    public String getEmissionCountry() {
        return emissionCountry;
    }

    public String getIdCode() {
        return idCode;
    }

    public IdType getIdType() {
        return idType;
    }

    public byte[] getIdPhoto() {
        return idPhoto;
    }

    public byte[] getValidationPhoto() {
        return validationPhoto;
    }

    public String getIdPhotoType() {
        return idPhotoType;
    }

    public String getValidationPhotoType() {
        return validationPhotoType;
    }
}
