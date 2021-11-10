package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.Student;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class ApplicationController {

    private List<Application> applications;
    private List<Application> approvedApplications;
    private List<Application> pendingApplications;

    public ApplicationController() {
    }
    
    @PostConstruct
    public void init() {
        
    }
    
    public List<Application> getApprovedApplications(Student student) {
        return null;
    }

}
