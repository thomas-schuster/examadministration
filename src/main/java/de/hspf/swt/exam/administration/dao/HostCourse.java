package de.hspf.swt.exam.administration.dao;

import javax.persistence.Entity;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class HostCourse extends Course {

    public HostCourse() {
    }

    public HostCourse(String courseId, String courseTitle, double ects) {
        super(courseId, courseTitle, ects);
    }

}
