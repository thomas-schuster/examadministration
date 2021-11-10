package de.hspf.swt.exam.administration.exceptions;

/**
 *
 * @author karl-heinz.rau
 */
public class OnlyOneHostCourseException extends Exception {

    public OnlyOneHostCourseException(int numberOfHomeCourses) {
        super("For " + numberOfHomeCourses
                + " Home Courses you can only transfer one Host Course");
    }

}
