package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.ApplicationItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class ApplicationItemFacade extends AbstractFacade<ApplicationItem> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public ApplicationItemFacade() {
        super(ApplicationItem.class);
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm( EntityManager em ) {
        this.em = em;
    }

}
