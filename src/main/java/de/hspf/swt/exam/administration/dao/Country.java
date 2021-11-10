package de.hspf.swt.exam.administration.dao;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class Country {

    @Id
    private String countryName;

    public Country() {
    }

    public Country( String countryName ) {
        this.countryName = countryName;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName( String countryName ) {
        this.countryName = countryName;
    }

}
