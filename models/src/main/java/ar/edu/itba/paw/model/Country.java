package ar.edu.itba.paw.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.util.Optional;
import java.util.OptionalInt;

@Entity
@Table(name="country")
public class Country {

    @Id
    @Column(name="iso", nullable = false)
    private String isoCode;

    @Column(name="name", nullable = false)
    private String name;

    public Country() {
        // Just for Hibernate!
    }

    public String getIsoCode() {
        return isoCode;
    }

    public String getName() {
        return name;
    }
}
