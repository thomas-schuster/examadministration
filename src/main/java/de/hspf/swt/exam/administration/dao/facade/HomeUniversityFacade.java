package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.Country;
import de.hspf.swt.exam.administration.dao.HomeUniversity;
import de.hspf.swt.exam.administration.dao.HostUniversity;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

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

    public HostUniversity getHostUniversityByName(String name) {
        TypedQuery<HostUniversity> query = em.createNamedQuery("HostUniversity.findAll", HostUniversity.class);
        query.setParameter("universityName", name);
        HostUniversity result = query.getSingleResult();
        return result;
    }
    
}
