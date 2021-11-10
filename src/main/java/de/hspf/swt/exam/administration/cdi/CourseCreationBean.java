package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.facade.HostCourseFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author thomas.schuster
 */
@Named(value = "courseCreationBean")
@ViewScoped
public class CourseCreationBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(CourseCreationBean.class);

    @EJB
    HostCourseFacade hostCourseBean;

    private String courseId;
    private String courseTitle;
    private Double courseEcts;

    public CourseCreationBean() {
    }

    public void createCourse() {
        logger.info("started to create new host course");
        HostCourse course = new HostCourse(courseId, courseTitle, courseEcts);
        hostCourseBean.create(course);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId( String courseId ) {
        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle( String courseTitle ) {
        this.courseTitle = courseTitle;
    }

    public Double getCourseEcts() {
        return courseEcts;
    }

    public void setCourseEcts( Double courseEcts ) {
        this.courseEcts = courseEcts;
    }

}
