package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
@NamedQueries({
    @NamedQuery(name="HostUniversity.findAll",
                query="SELECT hu FROM HostUniversity hu"),
    @NamedQuery(name="HostUniversity.findByName",
                query="SELECT hu FROM HostUniversity hu WHERE hu.universityName = :name"),
}) 
public class HostUniversity extends University implements Serializable {

    private static final long serialVersionUID = -5707339726747069456L;

    private double conversionMultiplier;

    @OneToMany(cascade = CascadeType.ALL)
    private List<HostCourse> courses;

    public HostUniversity() {
        courses = new ArrayList<>();
    }

    public HostUniversity(String universityName, String city, Country country) {
        super(universityName, city, country);
        courses = new ArrayList<>();
    }

    public HostUniversity(String universityName, String city, Country country, double conversionMultiplier) {
        super(universityName, city, country);
        this.conversionMultiplier = conversionMultiplier;
        courses = new ArrayList<>();
    }
    public void addHostCourse(HostCourse hostCourse) {
        courses.add(hostCourse);
    }

    public HostCourse createHostCourse(String courseId, String courseTitle, double credits) {
        HostCourse hostCourse = new HostCourse(courseId, courseTitle, credits);
        courses.add(hostCourse);
        return hostCourse;
    }
    public double getConversionMultiplier() {
        return conversionMultiplier;
    }

    public void setConversionMultiplier(double conversionMultiplier) {
        this.conversionMultiplier = conversionMultiplier;
    }

    public List<HostCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<HostCourse> courses) {
        this.courses = courses;
    }
}
