package ar.edu.itba.paw.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name="cryptocurrency")
public class Cryptocurrency {

    @Id
    @Column(name="code",nullable = false)
    private String code;

    @Column(name="commercial_name",nullable = false,length = 20)
    private String commercialName;

    public Cryptocurrency(){
        // Just for Hibernate!
    }

    public String getCode() {
        return code;
    }
    public String getCommercialName() {
        return commercialName;
    }

}
