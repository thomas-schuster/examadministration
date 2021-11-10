package de.hspf.swt.exam.administration.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class StudyProgram {

    @OneToMany(cascade = CascadeType.ALL)
    private List<HomeCourse> courses;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String programTitle;

    public StudyProgram() {
    }

    public StudyProgram(String programTitle) {
        this.programTitle = programTitle;
        courses = new ArrayList<>();
    }

    public void addCourse(HomeCourse course) {
        courses.add(course);
    }

    public HomeCourse createHomeCourse(String courseId, String courseTitle, double credits) {
        HomeCourse newHomeCourse = new HomeCourse(courseId, courseTitle, credits);
        courses.add(newHomeCourse);
        return newHomeCourse;
    }

    public List<HomeCourse> getCourses() {
        return courses;
    }

    public void setCourses(List<HomeCourse> courses) {
        this.courses = courses;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProgramTitle() {
        return programTitle;
    }

    public void setProgramTitle(String programTitle) {
        this.programTitle = programTitle;
    }

}
