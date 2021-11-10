package de.hspf.swt.exam.administration.dao;

import de.hspf.swt.exam.administration.exceptions.MoreThanOneHomeCourseWarning;
import de.hspf.swt.exam.administration.exceptions.MoreThanOneHostCourseWarning;
import de.hspf.swt.exam.administration.exceptions.OnlyOneHostCourseException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class LearningAgreementItem implements Serializable, Comparable<LearningAgreementItem> {

    private static final long serialVersionUID = -9188404637351245766L;
    private static final Logger logger = LogManager.getLogger(LearningAgreementItem.class);

    @JoinTable(name = "learningAgreementItem_homeCourse", joinColumns = {
        @JoinColumn(name = "LearningAgreementItem_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "courses_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<HomeCourse> homeCourses;
    @JoinTable(name = "learningAgreementItem_hostCourse", joinColumns = {
        @JoinColumn(name = "LearningAgreementItem_ID", referencedColumnName = "ID")}, inverseJoinColumns = {
        @JoinColumn(name = "courses_ID", referencedColumnName = "ID")})
    @ManyToMany
    private List<HostCourse> hostCourses;

    @Id
    private String id;

    private int posNo;

    public LearningAgreementItem() {
        id = UUID.randomUUID().toString();
        hostCourses = new ArrayList<>();
        homeCourses = new ArrayList<>();
    }

    public void addHomeCourse( HomeCourse homeCourse, boolean noWarning )
            throws MoreThanOneHomeCourseWarning {
        if ( homeCourses.isEmpty() ) {
            homeCourses.add(homeCourse);
        } else {
            if ( noWarning ) {
                homeCourses.add(homeCourse);
            } else {
                throw new MoreThanOneHomeCourseWarning();
            }
        }
    }

    public void addHostCourse( HostCourse hostCourse, boolean noWarning )
            throws OnlyOneHostCourseException, MoreThanOneHostCourseWarning {
        if ( hostCourses.isEmpty() ) {
            hostCourses.add(hostCourse);
        } else {
            logger.info("Size of HomeCourses is: " + homeCourses.size());
            if ( homeCourses.size() > 1 ) {
                throw new OnlyOneHostCourseException(homeCourses.size());
            } else {
                if ( noWarning ) {
                    hostCourses.add(hostCourse);
                } else {
                    throw new MoreThanOneHostCourseWarning();
                }
            }
        }
    }

    @Override
    public int compareTo( LearningAgreementItem learningAgreementItem ) {
        return new Integer(this.posNo).compareTo(learningAgreementItem.getPosNo());
    }

    public List<HomeCourse> getHomeCourses() {
        return homeCourses;
    }

    public void setHomeCourses( List<HomeCourse> homeCourses ) {
        this.homeCourses = homeCourses;
    }

    public List<HostCourse> getHostCourses() {
        return hostCourses;
    }

    public void setHostCourses( List<HostCourse> hostCourses ) {
        this.hostCourses = hostCourses;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

    public int getPosNo() {
        return posNo;
    }

    public void setPosNo( int posNo ) {
        this.posNo = posNo;
    }

}
