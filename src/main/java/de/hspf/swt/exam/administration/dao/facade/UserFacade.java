package de.hspf.swt.exam.administration.dao.facade;

import de.hspf.swt.exam.administration.dao.User;
import java.util.Optional;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class UserFacade extends AbstractFacade<User> {

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    public UserFacade() {
        super(User.class);
    }

    @Override
    public EntityManager getEntityManager() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

    public Optional<User> findByNameAndPassword(String name, String password) {
        CriteriaBuilder cbuilder = getEntityManager().getCriteriaBuilder();
        CriteriaQuery cq = cbuilder.createQuery();
        Root<User> root = cq.from(User.class);
        cq.select(root);
        ParameterExpression<String> user = cbuilder.parameter(String.class);
        ParameterExpression<String> userPass = cbuilder.parameter(String.class);
        cq.where(
                cbuilder.equal(root.get("userId"), user),
                cbuilder.equal(root.get("password"), userPass)
        );

        try {
            TypedQuery<User> query = em.createQuery(cq);
            query.setParameter(user, name);
            query.setParameter(userPass, password);

            return Optional.of(query.getSingleResult());
        } catch (NoResultException ex) {
            //log
        }
        return Optional.empty();
    }

}
