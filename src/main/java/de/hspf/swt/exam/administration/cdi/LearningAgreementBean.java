package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.control.LearningAgreementController;
import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.LearningAgreement;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.administration.dao.Student;
import java.io.Serializable;
import java.util.ArrayList;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author karl-heinz.rau
 * @author thomas.schuster
 *
 */
@Named(value = "learningAgreementBean")
@SessionScoped
public class LearningAgreementBean implements Serializable {

    private static final long serialVersionUID = -7582832515176544993L;
    private static final Logger logger = LogManager.getLogger(LearningAgreementBean.class);

    private ApplicationItem applicationItem;
    private ArrayList<ApplicationItem> applicationItems;
    private LearningAgreementItem currentLearningAgreementItem;
    private LearningAgreement learningAgreement;
    @EJB
    LearningAgreementController learningAgreementController;
    @Inject
    LoginBean loginBean;
    private boolean renderAddHomeCourseButton = false;
    private boolean renderAddHostCourseButton = false;
    private boolean renderCreateNextLAItem = false;
    private ArrayList<HomeCourse> selectableHomeCourses;
    private ArrayList<HostCourse> selectableHostCourses;
    private Student student;

    public LearningAgreementBean() {
    }

    private void adaptSelectableCourseLists(LearningAgreement learningAgreement) {
        learningAgreement.getLearningAgreementItems().stream().map((item) -> {
            item.getHomeCourses().forEach((homeCourse) -> {
                selectableHomeCourses.remove(homeCourse);
            });
            return item;
        }).forEachOrdered((item) -> {
            item.getHostCourses().forEach((hostCourse) -> {
                selectableHostCourses.remove(hostCourse);
            });
        });
    }

    public String doCreateLearningAgreement(ApplicationItem applicationItem) {
        this.applicationItem = applicationItem;
        selectableHomeCourses = learningAgreementController.getHomeCourses(applicationItem);
        selectableHostCourses = learningAgreementController.getHostCourses(applicationItem);
        if (learningAgreementController.checkIfLaExistsForAppplicationItem(applicationItem)) {
            learningAgreement = learningAgreementController.getLearningAgreement();
            adaptSelectableCourseLists(learningAgreement);
            renderAddHomeCourseButton = false;
            renderAddHostCourseButton = false;
            renderCreateNextLAItem = true;
        } else {
            learningAgreement = learningAgreementController.createLearningAgreement(applicationItem);
            currentLearningAgreementItem = learningAgreement.getLearningAgreementItems().get(0);
            renderAddHomeCourseButton = true;
            logger.info("LearningAgreement information, id: " + learningAgreement.getId() + ", created: " + learningAgreement.getDateOfCreation());
            logger.info("LearningAgreementItem information, id: " + currentLearningAgreementItem.getId());
        }
        return "createLA.xhtml";
    }

    public String doGetApprovedApplications() {
        applicationItems = (ArrayList<ApplicationItem>) learningAgreementController.getApprovedApplicationItems(student);

        switch (applicationItems.size()) {
            case 0:
                FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
                return "noApprovedApplicationFound.xhtml";
            default:
                return "selectApplicationItem.xhtml";
        }
    }

    public ApplicationItem getApplicationItem() {
        return applicationItem;
    }

    public void setApplicationItem(ApplicationItem applicationItem) {
        this.applicationItem = applicationItem;
    }

    public ArrayList<ApplicationItem> getApplicationItems() {
        return applicationItems;
    }

    public void setApplicationItems(ArrayList<ApplicationItem> applicationItems) {
        this.applicationItems = applicationItems;
    }

    public LearningAgreementItem getCurrentLearningAgreementItem() {
        return currentLearningAgreementItem;
    }

    public void setCurrentLearningAgreementItem(LearningAgreementItem currentLearningAgreementItem) {
        this.currentLearningAgreementItem = currentLearningAgreementItem;
    }

    public LearningAgreement getLearningAgreement() {
        return learningAgreement;
    }

    public void setLearningAgreement(LearningAgreement learningAgreement) {
        this.learningAgreement = learningAgreement;
    }

    public ArrayList<HomeCourse> getSelectableHomeCourses() {
        return selectableHomeCourses;
    }

    public void setSelectableHomeCourses(ArrayList<HomeCourse> selectableHomeCourses) {
        this.selectableHomeCourses = selectableHomeCourses;
    }

    public ArrayList<HostCourse> getSelectableHostCourses() {
        return selectableHostCourses;
    }

    public void setSelectableHostCourses(ArrayList<HostCourse> selectableHostCourses) {
        this.selectableHostCourses = selectableHostCourses;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public boolean isRenderAddHomeCourseButton() {
        return renderAddHomeCourseButton;
    }

    public void setRenderAddHomeCourseButton(boolean renderAddHomeCourseButton) {
        this.renderAddHomeCourseButton = renderAddHomeCourseButton;
    }

    public boolean isRenderAddHostCourseButton() {
        return renderAddHostCourseButton;
    }

    public void setRenderAddHostCourseButton(boolean renderAddHostCourseButton) {
        this.renderAddHostCourseButton = renderAddHostCourseButton;
    }

    public boolean isRenderCreateNextLAItem() {
        return renderCreateNextLAItem;
    }

    public void setRenderCreateNextLAItem(boolean renderCreateNextLAItem) {
        this.renderCreateNextLAItem = renderCreateNextLAItem;
    }

    @PostConstruct
    public void loadStudent() {
        String userId = loginBean.getUserData().getUserId();
        student = learningAgreementController.loadStudent(userId);
    }

}
