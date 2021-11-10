package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.HostUniversity;
import de.hspf.swt.exam.administration.dao.LearningAgreement;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.administration.dao.Student;
import de.hspf.swt.exam.administration.exceptions.MoreThanOneHomeCourseWarning;
import de.hspf.swt.exam.administration.exceptions.MoreThanOneHostCourseWarning;
import de.hspf.swt.exam.administration.exceptions.OnlyOneHostCourseException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author karl-heinz.rau
 */
@Stateful
public class LearningAgreementController {

    private static final Logger logger = LogManager.getLogger(LearningAgreementController.class);

    @PersistenceContext
    private EntityManager em;

    private LearningAgreement learningAgreement;
    private boolean update = false;

    //see Listing 4.9
    public List<ApplicationItem> getApprovedApplicationItems( Student student ) {
        return student.getApprovedApplicationItems();
    }

    public ArrayList<HomeCourse> getHomeCourses( ApplicationItem applicationItem ) {
        return new ArrayList<>(applicationItem.getHomeCourses());
    }
    
    public ArrayList<HostCourse> getHostCourses( ApplicationItem applicationItem ) {
        return new ArrayList<>(applicationItem.getHostCourses());
    }

    public HostCourse createNewHostCourse( HostUniversity hostUniverstiy, String courseId, String courseTitle, double ects ) {
        HostCourse hostCourse = hostUniverstiy.createHostCourse(courseId, courseTitle, ects);
        em.persist(hostCourse);
        em.merge(hostUniverstiy);
        logger.info("Angelegte LVA: " + hostCourse.getCourseTitle() + " " + hostCourse.getId());
        return hostCourse;
    }

    public boolean checkIfLaExistsForAppplicationItem( ApplicationItem applicationItem ) {
        boolean found;
        learningAgreement = applicationItem.getLearningAgreement();
//                getStudent().getLearningAgreementOfApplicationItem(applicationItem);
        if ( learningAgreement == null ) {
            found = false;
        } else {
            update = true;
            found = true;
        }
        return found;
    }

    public LearningAgreement getLearningAgreement() {
        return learningAgreement;
    }

    public LearningAgreementItem createLearningAgreementItem() {
        LearningAgreementItem newLearningAgreementItem = learningAgreement.createLearningAgreementItem();
        return newLearningAgreementItem;
    }

    public LearningAgreement createLearningAgreement( ApplicationItem applicationItem ) {
        learningAgreement = applicationItem.getStudent().createLearningAgreement(applicationItem);
        return learningAgreement;
    }

    public void addHostCourse( HostCourse hostCourse, boolean noWarning ) throws OnlyOneHostCourseException, MoreThanOneHostCourseWarning {
        learningAgreement.getLearningAgreementItems().get(learningAgreement.getLearningAgreementItems().size() - 1).addHostCourse(hostCourse, noWarning);
    }

    public void addHomeCourse( HomeCourse homeCourse, boolean noWarning ) throws MoreThanOneHomeCourseWarning {
        learningAgreement.getLearningAgreementItems().get(learningAgreement.getLearningAgreementItems().size() - 1).addHomeCourse(homeCourse, noWarning);
    }

    public LearningAgreementItem deleteLearningAgreementItem( int laItemNo ) {
        LearningAgreementItem laItemToBeDeleted = learningAgreement.removeLearningAgreementItem(laItemNo);
        learningAgreement.reNumberingLearningAgreementItems();
        if ( laItemToBeDeleted != null ) {
            LearningAgreementItem laItemToBeDeletedAlreadySaved = em.find(LearningAgreementItem.class, laItemToBeDeleted.getId());
            if ( laItemToBeDeletedAlreadySaved == null ) {
            } else {
                em.merge(learningAgreement);
                em.remove(laItemToBeDeletedAlreadySaved);
            }
        }
        return laItemToBeDeleted;
    }
    
    public LearningAgreement saveLearningAgreement() {
        if (update) {
            em.merge(learningAgreement);
        } else {
            em.persist(learningAgreement);
            em.merge(learningAgreement.getStudent());
        }
        return learningAgreement;
    }

}
