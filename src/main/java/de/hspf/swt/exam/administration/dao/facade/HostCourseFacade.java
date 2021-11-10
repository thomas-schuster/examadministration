package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.HostCourse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class HostCourseFacade extends AbstractFacade<HostCourse> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public HostCourseFacade() {
        super(HostCourse.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
