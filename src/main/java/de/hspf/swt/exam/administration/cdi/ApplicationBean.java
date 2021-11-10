package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.cdi.model.LanguageLevel;
import de.hspf.swt.exam.administration.control.LearningAgreementController;
import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.HostUniversity;
import de.hspf.swt.exam.administration.dao.facade.ApplicationFacade;
import de.hspf.swt.exam.administration.dao.facade.HostUniversityFacade;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import javax.inject.Inject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author thomas.schuster
 */
@Named(value = "applicationBean")
@ConversationScoped
public class ApplicationBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(ApplicationBean.class);

    @Inject
    private Conversation conversation;

    @Inject
    private LoginBean loginBean;
    @Inject
    private Instance<LearningAgreementBean> learningAgreementBean;

    private List<Application> applications;
    private List<ApplicationItem> approvedApplicationItems;
    private ArrayList<ApplicationItem> notApprovedApplicationItems;
    private ApplicationItem selectedApplicationItem;

    @EJB
    private LearningAgreementController learningAgreementController;
    @EJB
    private HostUniversityFacade hostUniversityManager;
    @EJB
    private ApplicationFacade applicationManager;
    
    private List<HostUniversity> hosts;
    private HostUniversity host;

    private String motivation;
    private Date startDate;
    private Date endDate;
    private List<Application> applicationItems;
    private String level;
    

    public ApplicationBean() {
    }

    @PostConstruct
    public void init() {
        applications = loginBean.getStudent().getApplications();
        approvedApplicationItems = loginBean.getStudent().getApprovedApplicationItems();
        notApprovedApplicationItems = loginBean.getStudent().getNotApprovedApplicationItems();
        applicationItems = loginBean.getStudent().getApplications();
        hosts = hostUniversityManager.findAll();
    }

    public void createApplication() {
        logger.info("started to create new application");
        Application newApp = loginBean.getStudent().createApplication(calculateSemester(startDate), calculateSemester(endDate), motivation, motivation);
        newApp.setDateOfCreation(new Date());
        newApp.setEnglishLanguageSkillLevel(level);
        logger.debug("host is: " + host.getUniversityName());
        newApp.createApplicationItem(host, 1);
        
        logger.info("saving application");
        applicationManager.create(newApp);
        
        notApprovedApplicationItems = loginBean.getStudent().getNotApprovedApplicationItems();
        
        
        applicationManager.findAll().forEach((Application app) -> {
            app.getApplicationItems().forEach((item) -> {
                logger.debug("university in application: " + item.getHostUniversity().getUniversityName());
            });
        });
        
        resetApplicationvalues();
    }

    private String calculateSemester(Date date) {
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        if (month < 4 | month >9) {
            int nextYear = year + 1;
            return "WS " + year + "/" + nextYear; 
        } else {
            return "SS " + year;
        }
    }

    public void handleReturn() {

    }

    public String navigate2ApprovedApplications(boolean isAll) {
        if (isAll) {
            return "selectAllApplicationItems.xhtml";
        }
        return navigate2ApprovedApplications();
    }
    
    public String navigate2ApprovedApplications() {
        if (approvedApplicationItems.isEmpty()) {
            return "noApprovedApplicationFound.xhtml";
        }
        return "selectApplicationItem.xhtml";
    }

    public String navigate2LearningAgreement(ApplicationItem applicationItem) {
        logger.info("navigate to learning agreement");
        conversation.begin();
        learningAgreementBean.get().initObjects(applicationItem);
        return "learningAgreementListView.xhtml";
    }

    public List<Application> getApplications() {
        return applications;
    }

    public void setApplications(List<Application> applications) {
        this.applications = applications;
    }

    public List<ApplicationItem> getApprovedApplicationItems() {
        return approvedApplicationItems;
    }

    public void setApprovedApplicationItems(List<ApplicationItem> approvedApplicationItems) {
        this.approvedApplicationItems = approvedApplicationItems;
    }

    public ArrayList<ApplicationItem> getNotApprovedApplicationItems() {
        return notApprovedApplicationItems;
    }

    public void setNotApprovedApplicationItems(ArrayList<ApplicationItem> notApprovedApplicationItems) {
        this.notApprovedApplicationItems = notApprovedApplicationItems;
    }
    
    public HostUniversity getHost() {
        return host;
    }

    public void setHost(HostUniversity host) {
        this.host = host;
    }

    public List<HostUniversity> getHosts() {
        return hosts;
    }

    public void setHosts(List<HostUniversity> hosts) {
        this.hosts = hosts;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    @Produces
    public ApplicationItem getSelectedApplicationItem() {
        return selectedApplicationItem;
    }

    public void setSelectedApplicationItem(ApplicationItem selectedApplicationItem) {
        this.selectedApplicationItem = selectedApplicationItem;
    }

    private void resetApplicationvalues() {
        motivation = null;
        startDate = null;
        endDate = null;
        host = null;
        level = null;
    }

    public LanguageLevel[] getLevels() {
        return LanguageLevel.values();
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }
   
}
