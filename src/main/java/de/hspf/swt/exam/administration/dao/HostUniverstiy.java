package de.hspf.swt.exam.administration.dao;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.TableGenerator;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class HostUniverstiy {

    @TableGenerator(
            name = "NextUniversityId",
            table = "IdGenerator",
            pkColumnName = "Class",
            valueColumnName = "ID",
            pkColumnValue = "University",
            initialValue = 1,
            allocationSize = 1)

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE,
            generator = "NextUniversityId")
    private int id;

    @ManyToOne
    private Country country;
    private String universityName;
    private String city;

    public HostUniverstiy() {
    }

    public HostUniverstiy( Country country, String universityName, String city ) {
        this();
        this.country = country;
        this.universityName = universityName;
        this.city = city;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry( Country country ) {
        this.country = country;
    }

    public String getUniversityName() {
        return universityName;
    }

    public void setUniversityName( String universityName ) {
        this.universityName = universityName;
    }

    public String getCity() {
        return city;
    }

    public void setCity( String city ) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }
}
