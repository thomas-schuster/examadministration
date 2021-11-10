package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class ApplicationItem implements Serializable {

    private static final long serialVersionUID = 8326994025842934045L;

    private boolean admitted;

    @ManyToOne
    private Application application;
    @ManyToOne
    private HostUniversity hostUniversity;
    @Id
    private String id;
    private int posNumber;
    private int priority;

    public ApplicationItem() {
        id = UUID.randomUUID().toString();
    }

    public ApplicationItem(int posNo, int priority, HostUniversity hostUniversity, Application application) {
        this();
        this.posNumber = posNo;
        this.priority = priority;
        this.hostUniversity = hostUniversity;
        this.application = application;
    }
    public Application getApplication() {
        return application;
    }
    public void setApplication(Application application) {
        this.application = application;
    }
    public List getHomeCourses() {
        return getStudent().getHomeCourses();
    }
    public List getHostCourses() {
        return hostUniversity.getCourses();
    }

    public HostUniversity getHostUniversity() {
        return hostUniversity;
    }

    public void setHostUniversity(HostUniversity hostUniversity) {
        this.hostUniversity = hostUniversity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getPosNumber() {
        return posNumber;
    }

    public void setPosNumber(int posNumber) {
        this.posNumber = posNumber;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public Student getStudent() {
        return application.getStudent();
    }

    public boolean isAdmitted() {
        return admitted;
    }

    public void setAdmitted(boolean admitted) {
        this.admitted = admitted;
    }

}
