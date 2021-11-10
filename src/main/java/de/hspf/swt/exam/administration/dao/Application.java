package de.hspf.swt.exam.administration.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class Application {

    @OneToMany(mappedBy = "application", cascade = {PERSIST, REMOVE})
    private List<ApplicationItem> applicationItems;

    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfCreation = new Date();
    private String englishLanguageSkillLevel;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String lastSemester;
    private String motivation;
    private String startSemester;
    @ManyToOne
    private Student student;

    public Application() {
        applicationItems = new ArrayList<>();
    }

    public Application(String startingSemester, String lastSemester, String englishLanguageSkillLevel, String motivation, Student student) {
        this();
        this.startSemester = startingSemester;
        this.lastSemester = lastSemester;
        this.englishLanguageSkillLevel = englishLanguageSkillLevel;
        this.motivation = motivation;
        this.student = student;
        this.dateOfCreation = new Date();
    }

    public ApplicationItem createApplicationItem(HostUniverstiy hostUniverstiy, int priority) {
        ApplicationItem newApplicationItem = new ApplicationItem(applicationItems.size() + 1, priority, hostUniverstiy, this);
        applicationItems.add(newApplicationItem);
        return newApplicationItem;
    }

    public List<ApplicationItem> getApplicationItems() {
        return applicationItems;
    }

    public void setApplicationItems(List<ApplicationItem> applicationItems) {
        this.applicationItems = applicationItems;
    }

    public ArrayList<ApplicationItem> getApprovedApplicationItems() {
        ArrayList<ApplicationItem> approvedApplicationItems = new ArrayList<>();
        for (ApplicationItem applicationItem : applicationItems) {
            if (applicationItem.isAdmitted()) {
                approvedApplicationItems.add(applicationItem);
            }
        }
        return approvedApplicationItems;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public String getEnglishLanguageSkillLevel() {
        return englishLanguageSkillLevel;
    }

    public void setEnglishLanguageSkillLevel(String englishLanguageSkillLevel) {
        this.englishLanguageSkillLevel = englishLanguageSkillLevel;
    }

    public int getId() {
        return id;
    }

    void setId(Long id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLastSemester() {
        return lastSemester;
    }

    public void setLastSemester(String lastSemester) {
        this.lastSemester = lastSemester;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getStartSemester() {
        return startSemester;
    }

    public void setStartSemester(String startSemester) {
        this.startSemester = startSemester;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
