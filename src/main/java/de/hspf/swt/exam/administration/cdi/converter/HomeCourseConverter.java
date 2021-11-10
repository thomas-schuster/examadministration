package de.hspf.swt.exam.administration.cdi.converter;

import de.hspf.swt.exam.administration.dao.Course;
import de.hspf.swt.exam.administration.dao.facade.HomeCourseFacade;
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
@Named(value = "homeCourseConverter")
@Dependent
@FacesConverter(forClass = Course.class)
public class HomeCourseConverter implements Converter, Serializable {

    @EJB
    HomeCourseFacade homeCourseBean;

    @Override
    public Object getAsObject( FacesContext context, UIComponent component, String value ) {
        if ( value == null || value.isEmpty() ) {
            return null;
        }
        return homeCourseBean.find(Integer.valueOf(value));

    }

    @Override
    public String getAsString( FacesContext context, UIComponent component, Object value ) {
        if ( !(value instanceof Course) ) {
            return "";
        }
        Integer id = ((Course) value).getId();
        return id.toString();
    }
}
