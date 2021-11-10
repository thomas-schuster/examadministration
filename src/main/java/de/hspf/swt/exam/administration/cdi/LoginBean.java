package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.control.LearningAgreementController;
import de.hspf.swt.exam.administration.control.LoginController;
import de.hspf.swt.exam.administration.dao.Student;
import de.hspf.swt.exam.administration.dao.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author karl-heinz.rau
 */
@Named
@SessionScoped
public class LoginBean implements Serializable {

    private static final Logger logger = LogManager.getLogger(LoginBean.class);
    private static final long serialVersionUID = 7730144564841961508L;
    private boolean loggedIn;

    @EJB
    private LoginController loginController;

    private User user;
    private Student student;

    public LoginBean() {
    }

    public String doLogin() {
        String forward, caption, msg;
        logger.debug("initiate validation");
        user = loginController.loadUser(user.getUserId(), user.getPassword());
        FacesContext context = FacesContext.getCurrentInstance();

        if ( user == null ) {
            logger.debug("Could not find user with id. Possibly credentials are wrong.");
            context.validationFailed();
            caption = "Validation Error";
            msg = "No user valid found, error with userId or password";
            context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, caption, msg));
            forward = "login.xhtml";
        } else {
            caption = "Validation Error";
            msg = "No user valid found, error with userId or password";
            context.addMessage(null, new FacesMessage(caption, msg));
            student = loginController.loadStudent(user.getUserId());
            forward = "loginsuccessful.xhtml";
            loggedIn = true;
        }

        return forward;
    }

    /**
     * *
     * This method will invalidate the current user session
     *
     * @return forward (redirect) to login or error page
     */
    public String doLogout() {
        logger.debug("intiated user logout");
        // Notice the redirect syntax. The forward slash means start at
        // the root of the web application.
        String forward = "/login?faces-redirect=true";

        // FacesContext provides access to other container managed objects,
        // such as the HttpServletRequest object, which is needed to perform
        // the logout operation.
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

        try {
            HttpSession session = request.getSession();
            session.invalidate();
            // this does not invalidate the session but does null out the user principle
            request.logout();
        } catch ( ServletException e ) {
            logger.error("Failed to logout user! " + e.getMessage());
            forward = "/loginerror?faces-redirect=true";
        }

        loggedIn = false;
        return forward;

    }

    private void resetData() {
        user = null;
    }

    public User getUser() {
        if ( user == null ) {
            user = new User();
        }
        return user;
    }

    public void setUser( User user ) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn( boolean loggedIn ) {
        this.loggedIn = loggedIn;
    }

    private void switchLoggedIn() {
        this.loggedIn = !loggedIn;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent( Student student ) {
        this.student = student;
    }
    
}
