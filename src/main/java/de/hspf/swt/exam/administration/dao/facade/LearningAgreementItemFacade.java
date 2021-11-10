package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class LearningAgreementItemFacade extends AbstractFacade<LearningAgreementItem> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public LearningAgreementItemFacade() {
        super(LearningAgreementItem.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
