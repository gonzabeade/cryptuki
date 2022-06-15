package ar.edu.itba.paw.model;
import ar.edu.itba.paw.model.parameterObject.KycInformationPO;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name="kyc")
public class KycInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "kyc_kyc_id_seq")
    @SequenceGenerator(sequenceName = "kyc_kyc_id_seq", name = "kyc_kyc_id_seq", allocationSize = 1)
    @Column(name="kyc_id", nullable = false)
    private Integer kycId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

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

    public KycInformation(KycInformationPO parameterObject, User user) {
        this.givenNames = parameterObject.getGivenNames();
        this.idPhoto = parameterObject.getIdPhoto();
        this.idCode = parameterObject.getIdCode();
        this.user = user;
        this.emissionCountry = parameterObject.getEmissionCountry();
        this.surnames = parameterObject.getSurnames();
        this.idType = parameterObject.getIdType();
        this.validationPhoto = parameterObject.getValidationPhoto();
        this.idPhotoType = parameterObject.getIdPhotoType();
        this.validationPhotoType = parameterObject.getValidationPhotoType();
    }

    public KycInformation() {
        // Just for Hibernate!
    }

    public int getKycId() {
        return kycId;
    }

    public User getUser() {
        return user;
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

    public void setStatus(KycStatus status) {
        this.status = status;
    }

}
