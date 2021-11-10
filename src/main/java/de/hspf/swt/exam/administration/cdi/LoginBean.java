package de.hspf.swt.exam.administration.cdi;

import de.hspf.swt.exam.administration.control.LoginController;
import de.hspf.swt.exam.administration.dao.User;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
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

    @EJB
    private LoginController loginController;
    private User userData;

    public LoginBean() {
    }

    public String doLogin() {
        String forward;
        logger.debug("Submitted userId is: " + userData.getUserId());
        userData = loginController.loadUser(userData.getUserId(), userData.getPassword());
        if (userData == null) {
            logger.debug("Could not find user with id. Possibly credentials are wrong.");

            FacesContext context = FacesContext.getCurrentInstance();
            String msg = " - No user found, error with userId or password";
            context.addMessage("loginForm:loginButton",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, msg, msg));
            forward = "login.xhtml";
        } else {
            forward = "loginsuccessful.xhtml";
        }
        return forward;
    }

    public User getUserData() {
        if (userData == null) {
            userData = new User();
        }
        return userData;
    }

    public void setUserData(User userData) {
        this.userData = userData;
    }

}
