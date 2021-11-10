package de.hspf.swt.exam.administration.dao;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class ApplicationItem {

    private int posNumber;
    private int priority;
    private boolean admitted;

    @ManyToOne
    private HostUniverstiy hostUniversity;
    @ManyToOne
    private Application application;
    @Id
    private String id;

    public ApplicationItem() {
        id = UUID.randomUUID().toString();
    }

    public ApplicationItem( int posNo, int priority, HostUniverstiy hostUniversity, Application application ) {
        this();
        this.posNumber = posNo;
        this.priority = priority;
        this.hostUniversity = hostUniversity;
        this.application = application;
    }

    public int getPosNumber() {
        return posNumber;
    }

    public void setPosNumber( int posNumber ) {
        this.posNumber = posNumber;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority( int priority ) {
        this.priority = priority;
    }

    public boolean isAdmitted() {
        return admitted;
    }

    public void setAdmitted( boolean admitted ) {
        this.admitted = admitted;
    }

    public HostUniverstiy getHostUniversity() {
        return hostUniversity;
    }

    public void setHostUniversity( HostUniverstiy hostUniversity ) {
        this.hostUniversity = hostUniversity;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication( Application application ) {
        this.application = application;
    }

    public String getId() {
        return id;
    }

    public void setId( String id ) {
        this.id = id;
    }

}
