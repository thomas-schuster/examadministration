package de.hspf.swt.exam.administration.cdi.converter;

import de.hspf.swt.exam.administration.dao.HostUniversity;
import de.hspf.swt.exam.administration.dao.facade.HostUniversityFacade;
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
@Named(value = "hostConverter")
@Dependent
@FacesConverter(forClass = HostUniversity.class)
public class HostConverter implements Converter, Serializable {


    @EJB
    HostUniversityFacade hostBean;

  
    @Override
    public Object getAsObject( FacesContext context, UIComponent component, String value ) {
        if ( value == null || value.isEmpty() ) {
            return null;
        }
        return (HostUniversity) hostBean.find(Integer.valueOf(value));
    }

    @Override
    public String getAsString( FacesContext context, UIComponent component, Object value ) {
        if ( !(value instanceof HostUniversity) ) {
            return "";
        }
        Integer id = ((HostUniversity) value).getId();
        return id.toString();
    }
}
