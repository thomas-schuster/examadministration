package de.hspf.swt.exam.administration.exceptions;

/**
 *
 * @author karl-heinz.rau
 */
public class MoreThanOneHostCourseWarning extends Exception {

    public MoreThanOneHostCourseWarning() {
        super("Warning: "
                + "Resubmit the additional Host-Course if more than one Host Course "
                + "should be transfered for one Home Course");
    }
    
}
