package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.LearningAgreement;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class LearningAgreementFacade extends AbstractFacade<LearningAgreement> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public LearningAgreementFacade() {
        super(LearningAgreement.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
