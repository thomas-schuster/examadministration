package de.hspf.swt.exam.administration.dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class Student {

    @OneToMany(mappedBy = "student")
    private List<Application> applications;
    private String firstName;
    private String lastName;
    @TableGenerator(
            name = "NextStudentId",
            table = "IdGenerator",
            pkColumnName = "Class",
            valueColumnName = "ID",
            pkColumnValue = "Student",
            initialValue = 100100,
            allocationSize = 1)

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "NextStudentId")
    private int studentID;

    public Student() {
        applications = new ArrayList<>();
    }

    public Student(String name, String vorname) {
        this();
        this.lastName = name;
        this.firstName = vorname;
    }

    public Student(String lastName, String firstName, int studentID) {
        this();
        this.lastName = lastName;
        this.firstName = firstName;
        this.studentID = studentID;
    }

    public Application createApplication(String startingSemester,
            String lastSemester, String englishLanguageSkills,
            String motivation) {
        Application newApplication = new Application(startingSemester,
                lastSemester, englishLanguageSkills, motivation, this);
        applications.add(newApplication);
        return newApplication;
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    //see Listing 4.11
    public ArrayList<ApplicationItem> getApprovedApplicationItems() {
        ArrayList<ApplicationItem> approvedApplicationItems = new ArrayList<>();
        for (Application application : applications) {
            approvedApplicationItems.addAll(application.getApprovedApplicationItems());
        }
        return approvedApplicationItems;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getStudentID() {
        return studentID;
    }

    public void setStudentID(int studentID) {
        this.studentID = studentID;
    }

}
