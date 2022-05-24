package ar.edu.itba.paw.persistence;

import javax.persistence.*;

@Entity
@Table(name="user_role")
public final class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_role_id_seq")
    @SequenceGenerator(sequenceName = "user_role_id_seq", name = "user_role_id_seq", allocationSize = 1)
    private int id;
    @Column(length = 20,nullable = false,unique = true)
    private String description;

    Role(){}

    protected Role(int id, String description) {
        this.id = id;
        this.description = description;
    }

    public int getId() {
        return id;
    }
    public String getDescription() {
        return description;
    }
}
