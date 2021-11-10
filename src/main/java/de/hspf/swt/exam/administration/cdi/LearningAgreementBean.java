package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.cdi.converter.LearningAgreementDocumentGenerator;
import de.hspf.swt.exam.administration.cdi.model.ListedLearningAgreementItem;
import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.LearningAgreement;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.administration.dao.facade.LearningAgreementFacade;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.OutputStream;
import javax.faces.application.FacesMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author karl-heinz.rau
 * @author thomas.schuster
 *
 */
@Named(value = "learningAgreementBean")
@ConversationScoped
public class LearningAgreementBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(LearningAgreementBean.class);
    private static final long serialVersionUID = -7582832515176544993L;

    @Inject
    private Conversation conversation;

    @Inject
    LoginBean loginBean;
    @Inject
    Instance<CourseBean> courseBean;

    @EJB
    LearningAgreementFacade learningAgreementManager;

    private ApplicationItem applicationItem;
    private LearningAgreement learningAgreement;
    private List<LearningAgreementItem> learningAgreementItems;

    public LearningAgreementBean() {
    }

    public void initObjects(ApplicationItem appItem) {
        applicationItem = appItem;
        initLearningAgreement();
        learningAgreementItems = learningAgreement.getLearningAgreementItems();
    }

    private void initLearningAgreement() {
        if (applicationItem.getLearningAgreement() == null) {
            logger.info("created new learning agreement for application item: " + applicationItem.getId());
            setLearningAgreement(loginBean.getStudent().createLearningAgreement(applicationItem));
        } else {
            setLearningAgreement(applicationItem.getLearningAgreement());
        }
    }

    public void navigate2Document() throws IOException {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpServletResponse res = (HttpServletResponse) facesContext.getExternalContext().getResponse();

        // Initialize response.
        res.reset(); // Some JSF component library or some Filter might have set some headers in the buffer beforehand. We want to get rid of them, else it may collide.
        res.setContentType("application/pdf"); // Check http://www.iana.org/assignments/media-types for all types. Use if necessary ServletContext#getMimeType() for auto-detection based on filename.

        // Write file to response.
        OutputStream output = res.getOutputStream();
        LearningAgreementDocumentGenerator con = new LearningAgreementDocumentGenerator();
        con.createPdf(learningAgreement, output);
        output.close();

        // Inform JSF to not take the response in hands.
        facesContext.responseComplete(); // Important! Else JSF will attempt to render the response which obviously will fail since it's already written with a file and closed.
    }

    public String navigate2Applications() {
        conversation.end();
        return "selectApplicationItems.xhtml";
    }

    public void navigate2LearningAgreementItem() {
        logger.info("initialize CourseBean");
        courseBean.get().init(getHomeCourses(), getHostCourses());
    }

    public void navigate2LearningAgreementItem(LearningAgreementItem item) {
        logger.info("open CourseBean");
        courseBean.get().open(getHomeCourses(), getHostCourses(), item);
    }

    public void navigate2HostCourseCreation() {
        logger.info("open host course creation dialog");

    }

    public void handleReturn() {
        logger.info("returned from Learing Agreement selection");
        if (courseBean.get().isSelectionComplete()) {
            logger.info("user selection is complete");
            LearningAgreementItem item = courseBean.get().returnLearningAgreementItem();
            if (!getLearningAgreementItems().contains(item)) {
                addLearningAgreementItem(item);
            }
            courseBean.get().cancelSelection();
        }

    }

    private List<HomeCourse> getHomeCourses() {
        List<HomeCourse> homeCourses = getApplicationItem().getHomeCourses();
        logger.info("number of home courses: " + homeCourses.size());
        // remove all home course that have already been selected in a learning agreement item for the current learning agreement
        getLearningAgreementItems().forEach((item) -> {
            homeCourses.removeAll(item.getHomeCourses());
        });
        logger.info("updated number of home courses: " + homeCourses.size());
        return homeCourses;
    }

    private List<HostCourse> getHostCourses() {
        List<HostCourse> hostCourses = getApplicationItem().getHostCourses();
        logger.info("number of host courses: " + hostCourses.size());
        // remove all host course that have already been selected in a learning agreement item for the current learning agreement
        getLearningAgreementItems().forEach((item) -> {
            hostCourses.removeAll(item.getHostCourses());
        });;
        logger.info("updated number of host courses: " + hostCourses.size());
        return hostCourses;
    }

    public void deleteLearningAgreementItem(LearningAgreementItem item) {
        logger.info("remove learning agreement item: " + item.getId());
        getLearningAgreementItems().remove(item);
    }

    public void saveLearningAgreement() {
        learningAgreementManager.edit(learningAgreement);
    }

    /**
     * *
     * This method is used to create a new or edit an existing
     * {@link de.hspf.swt.exam.administration.dao.LearningAgreement} for a given
     * {@link de.hspf.swt.exam.administration.dao.ApplicationItem}. When the
     * {@link de.hspf.swt.exam.administration.dao.LearningAgreement} has been
     * loaded, the method will return a next step (html page) to display for
     * further editiing.
     *
     * @param applicationItem - the
     * {@link de.hspf.swt.exam.administration.dao.ApplicationItem} to
     * create/edit {@link de.hspf.swt.exam.administration.dao.LearningAgreement}
     * for
     * @return the page to display in a next step
     */
    public String doEditLearningAgreement(ApplicationItem applicationItem) {
        return null;
    }

    public String doCreateNextLearningAgreementItem() {
        return null;
    }

    public String doDeleteLearningAgreementItem(int laItemNo) {
        return null;
    }

    public void onRowEdit(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("LearningAgreementItem Edited", ((ListedLearningAgreementItem) event.getObject()).getItemNo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onRowCancel(RowEditEvent event) {
        FacesMessage msg = new FacesMessage("Edit Cancelled", ((ListedLearningAgreementItem) event.getObject()).getItemNo());
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void onCellEdit(CellEditEvent event) {
        Object oldValue = event.getOldValue();
        Object newValue = event.getNewValue();

        if (newValue != null && !newValue.equals(oldValue)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed", "Old: " + oldValue + ", New:" + newValue);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public ApplicationItem getApplicationItem() {
        return applicationItem;
    }

    public void setApplicationItem(ApplicationItem applicationItem) {
        this.applicationItem = applicationItem;
    }

    public LearningAgreement getLearningAgreement() {
        return learningAgreement;
    }

    public void setLearningAgreement(LearningAgreement learningAgreement) {
        this.learningAgreement = learningAgreement;
    }

    public List<LearningAgreementItem> getLearningAgreementItems() {
        return learningAgreementItems;
    }

    public void setLearningAgreementItems(List<LearningAgreementItem> learningAgreementItems) {
        this.learningAgreementItems = learningAgreementItems;
    }

    public void addLearningAgreementItem(LearningAgreementItem item) {
        this.learningAgreementItems.add(item);
    }

    public void removeLearningAgreementItem(LearningAgreementItem item) {
        this.learningAgreementItems.remove(item);
    }

}
