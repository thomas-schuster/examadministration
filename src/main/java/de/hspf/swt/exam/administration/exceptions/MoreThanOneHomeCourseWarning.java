package de.hspf.swt.exam.administration.exceptions;

/**
 *
 * @author karl-heinz.rau
 */
public class MoreThanOneHomeCourseWarning extends Exception {

    public MoreThanOneHomeCourseWarning() {
        super("Warning: Resubmit the additional Home-Course if one Host Course should be transfered for more than one Home Course");
    }

}
