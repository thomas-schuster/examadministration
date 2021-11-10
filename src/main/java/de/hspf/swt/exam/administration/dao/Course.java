package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public abstract class Course implements Serializable, Comparable<Course> {

    private static final long serialVersionUID = -7402491253457741709L;

    private String courseId;
    private String courseTitle;
    private double ects;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    public Course() {
    }

    public Course(String courseId, String courseTitle, double ects) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
        this.ects = ects;
    }

    @Override
    public int compareTo(Course course) {
        return this.courseTitle.compareTo(course.getCourseTitle());
    }

    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Course) obj).getId();
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public double getEcts() {
        return ects;
    }

    public void setEcts(double ects) {
        this.ects = ects;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
