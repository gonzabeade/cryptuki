package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.KycStatus;

import javax.persistence.*;
import java.util.Arrays;

@Entity
@Table(name="kyc")
public final class KycInformation {

    @Id
    @Column(name="uname", nullable = false)
    private String username;

    @Column(name="given_names", nullable = false)
    private String givenNames;

    @Column(name="surnames", nullable = false)
    private String surnames;

    @Column(name="nationality", nullable = false)
    private String nationality;

    @Column(name="id_code", nullable = false)
    private String idCode;

    @Column(name="id_type", nullable = false)
    private String idType;

    @Column(name="id_photo", nullable = false)
    private byte[] idPhoto;

    @Column(name="validation_photo", nullable = false)
    private byte[] validationPhoto;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private KycStatus status = KycStatus.PEN;
    @Entity
    @Table(name="kyc")
    public static class KycInformationBuilder {

        @Id
        @Column(name="uname", nullable = false)
        private final String username;

        @Column(name="given_names", nullable = false)
        private final String givenNames;
        @Column(name="surnames", nullable = false)
        private final String surnames;

        @Column(name="nationality", nullable = false)
        private String nationality;
        @Column(name="id_code", nullable = false)
        private String idCode;
        @Column(name="id_type", nullable = false)
        private String idType;

        @Column(name="id_photo", nullable = false)
        @Basic(optional = false, fetch = FetchType.LAZY)
        private byte[] idPhoto;

        @Column(name="validation_photo", nullable = false)
        @Basic(optional = false, fetch = FetchType.LAZY)
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

        public KycInformationBuilder withIdCode(String idCode) {
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

    public KycInformation() {

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

    public void setStatus(KycStatus status) {
        this.status = status;
    }
}
