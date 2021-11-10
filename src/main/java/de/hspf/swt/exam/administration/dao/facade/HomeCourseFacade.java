package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.HomeCourse;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class HomeCourseFacade extends AbstractFacade<HomeCourse> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public HomeCourseFacade() {
        super(HomeCourse.class);
    }
    
    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm( EntityManager em ) {
        this.em = em;
    }

}
