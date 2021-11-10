package de.hspf.swt.exam.administration.appcontroller;

import de.hspf.swt.exam.administration.dao.Application;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class ApplicationFacade extends AbstractFacade<Application> {

    private EntityManager em;

    public ApplicationFacade() {
        super(Application.class);
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm( EntityManager em ) {
        this.em = em;
    }

}
