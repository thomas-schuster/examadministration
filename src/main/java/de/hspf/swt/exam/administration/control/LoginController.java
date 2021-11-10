package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author karl-heinz.rau
 */
@Stateless
public class LoginController {

    @PersistenceContext
    private EntityManager em;

    private static final Logger logger = LogManager.getLogger(LoginController.class);

    public User loadUser(String userId, String password) {
        logger.info("try to query and check user credentials for: " + userId);
        logger.info("password: " + password);
        try {
            TypedQuery<User> query = em.createNamedQuery("User.loginUser", User.class);
            query.setParameter("userId", userId);
            query.setParameter("password", password);
            User userSem = query.getSingleResult();
            return userSem;
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }

    }

    private int getNextIdForStudent() {
        String sql = "Select id from idgenerator where class='student'";
        Query query = em.createNativeQuery(sql);
        int studentId = (Integer) query.getSingleResult();
        studentId++;
        sql = "update idgenerator set id=" + studentId + " where class='student'";
        query = em.createNativeQuery(sql);
        query.executeUpdate();
        return studentId;
    }

}
