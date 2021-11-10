package de.hspf.swt.exam.util;

import java.io.IOException;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ThomasSchuster
 */
public class ViewContextUtil {

    public static FacesContext getFacesContext() {
        return FacesContext.getCurrentInstance();
    }

    public static ExternalContext getExternalContext() {
        return getFacesContext().getExternalContext();
    }

    public static HttpSession getSession() {
        return (HttpSession) getExternalContext().getSession(false);
    }

    public static HttpServletRequest getRequest() {
        return (HttpServletRequest) getExternalContext().getRequest();
    }

    /**
     * *
     * This method will redirect to any url (also outside the current
     * application
     *
     * @param url
     * @throws IOException
     */
    public static void redirect(String url) throws IOException {
        getExternalContext().redirect(url);
    }

    /**
     * *
     * This method will call the application's navigation handler to implement a
     * redirect. The method will trigger a faces redirect.
     *
     * @param page to navigate to
     *
     */
    public static void internalRedirect(String page) {
        getFacesContext().getApplication().getNavigationHandler().handleNavigation(getFacesContext(), getFacesContext().getViewRoot().getViewId(), page + "?faces-redirect=true");
    }

}