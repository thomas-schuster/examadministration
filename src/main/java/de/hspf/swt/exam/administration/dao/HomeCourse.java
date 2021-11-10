package de.hspf.swt.exam.administration.dao;

import javax.persistence.Entity;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class HomeCourse extends Course {

    public HomeCourse() {
    }

    public HomeCourse(String courseId, String courseTitle, double ects) {
        super(courseId, courseTitle, ects);
    }

}
