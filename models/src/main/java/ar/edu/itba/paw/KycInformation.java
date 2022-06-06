package ar.edu.itba.paw;

import ar.edu.itba.paw.persistence.Image;

import java.util.Arrays;

public class KycInformation {

    private final String username;

    private final String givenNames;

    private final String surnames;

    private final String nationality;

    private final String idCode;

    private final String idType;

    private final byte[] idPhoto;

    private final byte[] validationPhoto;

    public static class KycInformationBuilder {

        private final String username;

        private final String givenNames;

        private final String surnames;

        private String nationality;

        private String idCode;

        private String idType;

        private byte[] idPhoto;

        private byte[] validationPhoto;

        public KycInformationBuilder(String username, String givenNames, String surnames) {
            this.username = username;
            this.givenNames = givenNames;
            this.surnames = surnames;
        }

        public KycInformationBuilder withNationality(String nationality) {
            this.nationality = nationality;
            return this;
        }

        public KycInformationBuilder withIdCode(String isCode) {
            this.idCode = idCode;
            return this;
        }

        public KycInformationBuilder withIdType(String idType) {
            this.idType = idType;
            return this;
        }

        public KycInformationBuilder withIdPhoto(byte[] idPhoto) {
            this.idPhoto = idPhoto;
            return this;
        }

        public KycInformationBuilder withValidationPhoto(byte[] validationPhoto) {
            this.validationPhoto = validationPhoto;
            return this;
        }

        public KycInformation build() {
            return new KycInformation(this);
        }

        @Override
        public String toString() {
            return "KycInformationBuilder{" +
                    "username='" + username + '\'' +
                    ", givenNames='" + givenNames + '\'' +
                    ", surnames='" + surnames + '\'' +
                    ", nationality='" + nationality + '\'' +
                    ", idCode='" + idCode + '\'' +
                    ", idType='" + idType + '\'' +
                    ", idPhoto=" + Arrays.toString(idPhoto) +
                    ", validationPhoto=" + Arrays.toString(validationPhoto) +
                    '}';
        }
    }

    private KycInformation(KycInformationBuilder builder) {
        this.givenNames = builder.givenNames;
        this.idPhoto = builder.idPhoto;
        this.idCode = builder.idCode;
        this.nationality = builder.nationality;
        this.surnames = builder.surnames;
        this.username = builder.username;
        this.idType = builder.idType;
        this.validationPhoto = builder.validationPhoto;
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

    public String getNationality() {
        return nationality;
    }

    public String getIdCode() {
        return idCode;
    }

    public String getIdType() {
        return idType;
    }

    public byte[] getIdPhoto() {
        return idPhoto;
    }

    public byte[] getValidationPhoto() {
        return validationPhoto;
    }
}
