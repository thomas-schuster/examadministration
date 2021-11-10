package de.hspf.swt.exam.administration.dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;

/**
 *
 * @author karl-heinz.rau
 */
@Entity
public class LearningAgreement implements Serializable {

    private static final long serialVersionUID = 8028900872366356030L;

    @OneToOne
    private ApplicationItem applicationItem;
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date dateOfCreation;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<LearningAgreementItem> learningAgreementItems;

    public LearningAgreement() {
        dateOfCreation = new Date();
        learningAgreementItems = new ArrayList<>();
    }

    public LearningAgreement(ApplicationItem applicationItem) {
        this();
        this.applicationItem = applicationItem;
    }

    public final LearningAgreementItem createLearningAgreementItem() {
        LearningAgreementItem learningAgreementItem = new LearningAgreementItem();
        learningAgreementItem.setPosNumber(learningAgreementItems.size() + 1);
        learningAgreementItems.add(learningAgreementItem);
        return learningAgreementItem;
    }

    public ApplicationItem getApplicationItem() {
        return applicationItem;
    }

    public void setApplicationItem(ApplicationItem applicationItem) {
        this.applicationItem = applicationItem;
    }

    public Date getDateOfCreation() {
        return dateOfCreation;
    }

    public void setDateOfCreation(Date dateOfCreation) {
        this.dateOfCreation = dateOfCreation;
    }

    public List getHomeCourses() {
        return applicationItem.getHomeCourses();
    }

    public List getHostCourses() {
        return applicationItem.getHostCourses();
    }

    public HostUniversity getHostUniverstiy() {
        return applicationItem.getHostUniversity();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<LearningAgreementItem> getLearningAgreementItems() {
        return learningAgreementItems;
    }

    public void setLearningAgreementItems(List<LearningAgreementItem> learningAgreementItems) {
        this.learningAgreementItems = learningAgreementItems;
    }

    public Student getStudent() {
        return applicationItem.getStudent();
    }

    public void reNumberingLearningAgreementItems() {
        int i = 1;
        for (LearningAgreementItem item : learningAgreementItems) {
            item.setPosNumber(i);
            i++;
        }
    }

    public LearningAgreementItem removeLearningAgreementItem(int laItemNo) {
        LearningAgreementItem itemFound = null;
        for (LearningAgreementItem item : learningAgreementItems) {
            if (item.getPosNumber() == laItemNo) {
                itemFound = item;
                learningAgreementItems.remove(itemFound);
                break;
            }
        }
        return itemFound;
    }

}
