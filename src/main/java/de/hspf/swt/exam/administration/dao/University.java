package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public abstract class University implements Serializable {

    private static final long serialVersionUID = -5477584329796448168L;

    private String city;
    @ManyToOne
    private Country country;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String universityName;

    public University() {
    }

    public University(String universityName, String city, Country country) {
        this.universityName = universityName;
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName(String name) {
        this.universityName = name;
    }

}
