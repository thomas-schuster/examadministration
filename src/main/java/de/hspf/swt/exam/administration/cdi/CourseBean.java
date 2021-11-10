package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.administration.dao.facade.HomeCourseFacade;
import de.hspf.swt.exam.administration.dao.facade.HostCourseFacade;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.Size;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author thomas.schuster
 */
@Named(value = "courseBean")
@ConversationScoped
public class CourseBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(CourseBean.class);

    @Inject
    LearningAgreementBean learningAgreementBean;

    @EJB
    HomeCourseFacade homeCourseBean;
    @EJB
    HostCourseFacade hostCourseBean;

    private List<HomeCourse> homeCourses;
    @Size(min = 1)
    private List<HomeCourse> selectedHomeCourses;
    private List<HostCourse> hostCourses;
    @Size(min = 1)
    private List<HostCourse> selectedHostCourses;
    private boolean selectionComplete;
    private LearningAgreementItem item;

    public CourseBean() {
    }

    public void init(List<HomeCourse> homeCourses, List<HostCourse> hostCourses ) {
        item = new LearningAgreementItem();
        this.homeCourses = homeCourses;
        this.hostCourses = hostCourses;
        selectedHomeCourses = new ArrayList<>();
        selectedHostCourses = new ArrayList<>();
    }
    
    public void open(List<HomeCourse> availableHomeCourses, List<HostCourse> availableHostCourses, LearningAgreementItem item ) {
        this.item = item;
        this.homeCourses = availableHomeCourses;
        item.getHomeCourses().forEach((course) -> { 
            this.homeCourses.add(course);
        });
        this.hostCourses = availableHostCourses;
        item.getHostCourses().forEach((course) -> {
            this.hostCourses.add(course);
        });
        this.selectedHomeCourses = selectedHomeCourses;
        this.selectedHostCourses = selectedHostCourses;
    }

    public LearningAgreementItem returnLearningAgreementItem() {
        
        item.setHomeCourses(selectedHomeCourses);
        item.setHostCourses(selectedHostCourses);
        selectedHomeCourses = new ArrayList<>();
        selectedHostCourses = new ArrayList<>();
        
        return item;
    }

    public void finishSelection() {
        selectionComplete = true;
    }

    public void cancelSelection() {
        logger.info("aborted or finished learning agreement item creation");
        selectionComplete = false;
    }

    public void printContents() {
        logger.info("print pressed");
        selectedHomeCourses.forEach((course) -> {
            logger.info("Selected Home Course: " + course.getCourseTitle());
        });
        selectedHostCourses.forEach((course) -> {
            logger.info("Selected Host Course: " + course.getCourseTitle());
        });
    }

    public List<HomeCourse> getHomeCourses() {
        return homeCourses;
    }

    public void setHomeCourses( ArrayList<HomeCourse> homeCourses ) {
        this.homeCourses = homeCourses;
    }

    public List<HomeCourse> getSelectedHomeCourses() {
        return selectedHomeCourses;
    }

    public void setSelectedHomeCourses( ArrayList<HomeCourse> selectedHomeCourses ) {
        this.selectedHomeCourses = selectedHomeCourses;
    }

    public List<HostCourse> getHostCourses() {
        return hostCourses;
    }

    public void setHostCourses( ArrayList<HostCourse> hostCourses ) {
        this.hostCourses = hostCourses;
    }

    public List<HostCourse> getSelectedHostCourses() {
        return selectedHostCourses;
    }

    public void setSelectedHostCourses( ArrayList<HostCourse> selectedHostCourses ) {
        this.selectedHostCourses = selectedHostCourses;
    }

    public boolean isSelectionComplete() {
        return selectionComplete;
    }

    public void setSelectionComplete( boolean selectionComplete ) {
        this.selectionComplete = selectionComplete;
    }

}
