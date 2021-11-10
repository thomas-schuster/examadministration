package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.HostUniversity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class HostUniversityFacade extends AbstractFacade<HostUniversity> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public HostUniversityFacade() {
        super(HostUniversity.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
