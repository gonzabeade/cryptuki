package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.IdType;
import ar.edu.itba.paw.KycStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="kyc")
public final class KycInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kyc_kyc_id_seq")
    @SequenceGenerator(sequenceName = "kyc_kyc_id_seq", name = "kyc_kyc_id_seq", allocationSize = 1)
    @Column(name="kyc_id", nullable = false)
    private Integer kycId;

    @Column(name="uname", nullable = false)
    private String username;

    @Column(name="given_names", nullable = false)
    private String givenNames;

    @Column(name="surnames", nullable = false)
    private String surnames;

    @Column(name="emission_country", nullable = false)
    private String emissionCountry;

    @Column(name="id_code", nullable = false)
    private String idCode;

    @Enumerated(EnumType.STRING)
    @Column(name="id_type", nullable = false)
    private IdType idType;

    @Column(name="id_photo", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] idPhoto;

    @Column(name="validation_photo", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] validationPhoto;

    @Column(name="id_photo_type", nullable = false)
    private String idPhotoType;

    @Column(name="validation_photo_type", nullable = false)
    private String validationPhotoType;

    @Column(name = "kyc_date", nullable = false, insertable = false)
    private LocalDateTime kycDate;

    @Enumerated(EnumType.STRING)
    @Column(name="status", nullable = false)
    private KycStatus status = KycStatus.PEN;

    public static class KycInformationBuilder {

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

        public KycInformationBuilder(String username, String givenNames, String surnames) {
            this.username = username;
            this.givenNames = givenNames;
            this.surnames = surnames;
        }

        public KycInformationBuilder withEmissionCountry(String emissionCountry) {
            this.emissionCountry = emissionCountry;
            return this;
        }


        public KycInformationBuilder withIdCode(String idCode) {
            this.idCode = idCode;
            return this;
        }

        public KycInformationBuilder withIdType(IdType idType) {
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

        public KycInformationBuilder withIdPhotoType(String idPhotoType) {
            this.idPhotoType = idPhotoType;
            return this;
        }

        public KycInformationBuilder withValidationPhotoType(String validationPhotoType) {
            this.validationPhotoType = validationPhotoType;
            return this;
        }

        protected KycInformation build() {
            return new KycInformation(this);
        }
    }

    private KycInformation(KycInformationBuilder builder) {
        this.givenNames = builder.givenNames;
        this.idPhoto = builder.idPhoto;
        this.idCode = builder.idCode;
        this.emissionCountry = builder.emissionCountry;
        this.surnames = builder.surnames;
        this.username = builder.username;
        this.idType = builder.idType;
        this.validationPhoto = builder.validationPhoto;
        this.idPhotoType = builder.idPhotoType;
        this.validationPhotoType = builder.validationPhotoType;
    }

    public KycInformation() {
        // Just for Hibernate!
    }

    public int getKycId() {
        return kycId;
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

    public LocalDateTime getKycDate() {
        return kycDate;
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

    public KycStatus getStatus() {
        return status;
    }

    protected void setStatus(KycStatus status) {
        this.status = status;
    }

}
