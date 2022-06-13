package ar.edu.itba.paw.model;


import javax.persistence.*;

@Entity
@Table(name="profile_pic")
public class ProfilePicture {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "profile_pic_pic_id_seq")
    @SequenceGenerator(sequenceName = "profile_pic_pic_id_seq", name = "profile_pic_pic_id_seq", allocationSize = 1)
    @Column(name="pic_id", nullable = false)
    private Integer picId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @Column(name="image_data", nullable = false)
    @Basic(fetch = FetchType.LAZY)
    private byte[] bytes;

    @Column(name="image_type", nullable = false)
    private String imageType;

    public ProfilePicture() {
        // Just for Hibernate!
    }

    public ProfilePicture(User user, byte[] bytes, String imageType) {
        this.user = user;
        this.bytes = bytes;
        this.imageType = imageType;
    }

    public Integer getPicId() {
        return picId;
    }

    public User getUser() {
        return user;
    }

    public byte[] getBytes() {
        return bytes;
    }

    public String getImageType() {
        return imageType;
    }
}
