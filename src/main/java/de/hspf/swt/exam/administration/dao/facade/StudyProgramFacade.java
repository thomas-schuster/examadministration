package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.StudyProgram;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class StudyProgramFacade extends AbstractFacade<StudyProgram> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public StudyProgramFacade() {
        super(StudyProgram.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
