package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.HomeUniversity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class HomeUniversityFacade extends AbstractFacade<HomeUniversity> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public HomeUniversityFacade() {
        super(HomeUniversity.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
