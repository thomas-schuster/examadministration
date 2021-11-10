package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.Application;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class ApplicationFacade extends AbstractFacade<Application> {

    @PersistenceContext(unitName = "examAdminPU")
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
