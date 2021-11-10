package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.LearningAgreement;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.administration.dao.Student;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author karl-heinz.rau
 */
@Stateful
public class LearningAgreementController {

    @PersistenceContext
    private EntityManager em;

    private LearningAgreement learningAgreement;
    private boolean update = false;

    //see Listing 4.9
    public List<ApplicationItem> getApprovedApplicationItems(Student student) {
        return student.getApprovedApplicationItems();
    }

    //see Listing 5.14
    public Student loadStudent(String userId) {
        TypedQuery<Student> query = em.createNamedQuery("Student.findByUserid", Student.class);
        query.setParameter("userid", userId);
        Student student = query.getSingleResult();
        return student;
    }

    public ArrayList<HomeCourse> getHomeCourses(ApplicationItem applicationItem) {
        return new ArrayList<>(applicationItem.getHomeCourses());
    }

    public ArrayList<HostCourse> getHostCourses(ApplicationItem applicationItem) {
        return new ArrayList<>(applicationItem.getHostCourses());
    }

    public boolean checkIfLaExistsForAppplicationItem(ApplicationItem applicationItem) {
        boolean found;
        learningAgreement = applicationItem.getStudent().getLearningAgreementOfApplicationItem(applicationItem);
        if (learningAgreement == null) {
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
//        em.merge(learningAgreement);
        return newLearningAgreementItem;
    }

    public LearningAgreement createLearningAgreement(ApplicationItem applicationItem) {
        learningAgreement = applicationItem.getStudent().createLearningAgreement(applicationItem);
//        em.persist(learningAgreement);
//        em.merge(applicationItem.getStudent());
        return learningAgreement;
    }

}
