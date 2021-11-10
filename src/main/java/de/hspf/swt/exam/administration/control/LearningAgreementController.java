package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.Student;
import java.util.List;

/**
 *
 * @author karl-heinz.rau
 */
public class LearningAgreementController {

    //see Listing 4.9
    public List<ApplicationItem> getApprovedApplicationItems(Student student) {
        return student.getApprovedApplicationItems();
    }

}
