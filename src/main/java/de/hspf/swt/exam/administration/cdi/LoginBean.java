package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.control.LoginController;
import de.hspf.swt.exam.administration.dao.Student;
import de.hspf.swt.exam.administration.dao.User;
import de.hspf.swt.exam.util.ViewContextUtil;
import java.io.IOException;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.AuthenticationStatus;
import static javax.security.enterprise.AuthenticationStatus.NOT_DONE;
import static javax.security.enterprise.AuthenticationStatus.SEND_CONTINUE;
import static javax.security.enterprise.AuthenticationStatus.SEND_FAILURE;
import static javax.security.enterprise.AuthenticationStatus.SUCCESS;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Inject
    private SecurityContext securityContext;


    private User user;
    private Student student;

    public LoginBean() {
    }

    public String doLogin() throws IOException {
        String forward, caption, msg;
        logger.debug("initiate validation");
        user = loginController.loadUser(user.getUserId(), user.getPassword());
       
        forward = "login.xhtml";
        switch (continueAuthentication()) {
            case SEND_CONTINUE:
                ViewContextUtil.getFacesContext().responseComplete();
                break;
            case SEND_FAILURE:
                logger.debug("Could not find user with id. Possibly credentials are wrong.");
                ViewContextUtil.getFacesContext().validationFailed();
                
                caption = "Validation Error";
                msg = "No user valid found, error with userId or password";
                ViewContextUtil.getFacesContext().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, caption, msg));
                
                break;
            case SUCCESS:
                logger.debug("Login successful, for user: ".concat(user.getUserId()));
                student = loginController.loadStudent(user.getUserId());
                ViewContextUtil.getFacesContext().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_INFO, "Login succeed", null));
                ViewContextUtil.getFacesContext().getExternalContext().redirect(ViewContextUtil.getFacesContext().getExternalContext().getRequestContextPath() + "/app/loginsuccessful.xhtml");
                loggedIn = true;
                break;
            case NOT_DONE:
        }

        return forward;
    }

    private AuthenticationStatus continueAuthentication() {
        return securityContext.authenticate(
                (HttpServletRequest) ViewContextUtil.getFacesContext().getExternalContext().getRequest(),
                (HttpServletResponse) ViewContextUtil.getFacesContext().getExternalContext().getResponse(),
                AuthenticationParameters.withParams().credential(new UsernamePasswordCredential(user.getUserId(), user.getPassword()))
        );
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
        } catch (ServletException e) {
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
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }

    private void switchLoggedIn() {
        this.loggedIn = !loggedIn;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

}
