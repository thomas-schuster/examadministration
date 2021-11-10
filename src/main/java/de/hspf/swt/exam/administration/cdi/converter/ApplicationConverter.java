package de.hspf.swt.exam.administration.cdi.converter;

import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.facade.ApplicationFacade;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.enterprise.context.Dependent;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;
import javax.inject.Named;

/**
 *
 * @author thomas.schuster
 */
@Named(value = "appConverter")
@Dependent
@FacesConverter(forClass = Application.class)
public class ApplicationConverter implements Converter, Serializable {


    @EJB
    ApplicationFacade applicationBean;

  
    @Override
    public Object getAsObject( FacesContext context, UIComponent component, String value ) {
        if ( value == null || value.isEmpty() ) {
            return null;
        }
        return applicationBean.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString( FacesContext context, UIComponent component, Object value ) {
        if ( !(value instanceof Application) ) {
            return "";
        }
        Integer id = ((Application) value).getId();
        return id.toString();
    }
}
