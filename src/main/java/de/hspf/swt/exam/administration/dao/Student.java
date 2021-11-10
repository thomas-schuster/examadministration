package de.hspf.swt.exam.administration.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

/**
 *
 * @author karl-heinz.rau
 * @author thomas.schuster
 *
 */
@Entity
@NamedQuery(name = "Student.findByUserid", query = "SELECT s FROM Student s WHERE s.userId = :userid")
public class Student extends User {

    @OneToMany(mappedBy = "student")
    private List<Application> applications;
    private String firstName;
    @ManyToOne
    private HomeUniversity homeUniversity;
    private String lastName;

    @OneToMany
    private List<LearningAgreement> learningAgreements;
    @Column(unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int studentID;
    @ManyToOne
    private StudyProgram studyProgram;

    public Student() {
        super();
        applications = new ArrayList<>();
        learningAgreements = new ArrayList<>();
    }

    public Student(String lastName, String firstName) {
        applications = new ArrayList<>();
        learningAgreements = new ArrayList<>();
        this.lastName = lastName;
        this.firstName = firstName;
    }

    public Student(String lastName, String firstName, int studentID) {
        applications = new ArrayList<>();
        learningAgreements = new ArrayList<>();
        this.lastName = lastName;
        this.firstName = firstName;
        this.studentID = studentID;
    }

    public Student(String userId, String password, int studentId, String name, String vorname, StudyProgram studyProgram, HomeUniversity homeUniversity) {
        super(userId, password);
        applications = new ArrayList<>();
        learningAgreements = new ArrayList<>();
        this.studentID = studentId;
        this.lastName = name;
        this.firstName = vorname;
        this.studyProgram = studyProgram;
        this.homeUniversity = homeUniversity;
    }

    public Application createApplication(String startingSemester, String lastSemester, String englishLanguageSkills, String motivation) {
        Application newApplication = new Application(startingSemester, lastSemester, englishLanguageSkills, motivation, this);
        applications.add(newApplication);
        return newApplication;
    }

    public LearningAgreement createLearningAgreement(ApplicationItem applicationItem) {
        LearningAgreement learningAgreement = new LearningAgreement(applicationItem);
        learningAgreements.add(learningAgreement);
        return learningAgreement;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public ArrayList<ApplicationItem> getApprovedApplicationItems() {
        ArrayList<ApplicationItem> approvedApplicationItems = new ArrayList<>();
        for (Application application : applications) {
            approvedApplicationItems.addAll(application.getApprovedApplicationItems());
        }
        return approvedApplicationItems;
    }

    public ArrayList<ApplicationItem> getApprovedApplicationItemsInitial() {
        return null;
    }

    public List getCourses() {
        return studyProgram.getCourses();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public List getHomeCourses() {
        return studyProgram.getCourses();
    }

    public HomeUniversity getHomeUniversity() {
        return homeUniversity;
    }

    public void setHomeUniversity(HomeUniversity homeUniversity) {
        this.homeUniversity = homeUniversity;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LearningAgreement getLearningAgreementOfApplicationItem(ApplicationItem applicationItem) {
        for (LearningAgreement learningAgreement : learningAgreements) {
            if (learningAgreement.getApplicationItem().equals(applicationItem)) {
                return learningAgreement;
            }
        }
        return null;
    }

    public List<LearningAgreement> getLearningAgreements() {
        return learningAgreements;
    }

    public void setLearningAgreements(List<LearningAgreement> learningAgreements) {
        this.learningAgreements = learningAgreements;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

    public StudyProgram getStudyProgram() {
        return studyProgram;
    }

    public void setStudyProgram(StudyProgram studyProgram) {
        this.studyProgram = studyProgram;
    }

}
