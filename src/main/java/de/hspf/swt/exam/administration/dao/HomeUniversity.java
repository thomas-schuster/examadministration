package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class HomeUniversity extends University implements Serializable {

    private static final long serialVersionUID = 2314058275014300640L;

    @OneToMany
    private List<HostUniversity> hostUniversities;
    @OneToMany(mappedBy = "homeUniversity")
    private List<Student> students;
    @OneToMany
    private List<StudyProgram> studyPrograms;

    public HomeUniversity() {
        studyPrograms = new ArrayList<>();
        hostUniversities = new ArrayList<>();
        students = new ArrayList<>();
    }

    public HomeUniversity(String universityName, String city, Country country) {
        super(universityName, city, country);
        studyPrograms = new ArrayList<>();
        hostUniversities = new ArrayList<>();
        students = new ArrayList<>();
    }

    public HostUniversity createHostUniverstiy(String universityName, String city, Country country, double conversionMultiplier) {
        HostUniversity newHostUniverstiy = new HostUniversity(universityName, city, country, conversionMultiplier);
        hostUniversities.add(newHostUniverstiy);
        return newHostUniverstiy;
    }

    public Student createStudent(String userId, String lastName, String firstname, int studentId, String password, StudyProgram studyProgram) {
        Student newStudent = new Student(userId, password, studentId, firstname, firstname, studyProgram, this);
        students.add(newStudent);
        return newStudent;
    }

    public StudyProgram createStudyProgram(String title) {
        StudyProgram newStudyProgram = new StudyProgram(title);
        studyPrograms.add(newStudyProgram);
        return newStudyProgram;
    }

    public List<HostUniversity> getHostUniversities() {
        return hostUniversities;
    }

    public void setHostUniversities(List<HostUniversity> hostUniversities) {
        this.hostUniversities = hostUniversities;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<StudyProgram> getStudyPrograms() {
        return studyPrograms;
    }

    public void setStudyPrograms(List<StudyProgram> studyPrograms) {
        this.studyPrograms = studyPrograms;
    }

}
