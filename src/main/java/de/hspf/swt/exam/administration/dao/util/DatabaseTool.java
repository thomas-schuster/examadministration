package de.hspf.swt.exam.administration.dao.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author thomas.schuster
 */
@Stateless
public class DatabaseTool {

    private static final Logger logger = LogManager.getLogger(DatabaseTool.class);

    @PersistenceContext(unitName = "examAdminPU")
    private EntityManager em;

    @Resource
    private UserTransaction utx;

    public DatabaseTool() {
    }

    /**
     * *
     * Be careful! This method will delete the complete application database and recreate an empty one.
     *
     */
    public void swipeDatabase() {

        InputStream sqlFileInputStream = getClass().getClassLoader().getResourceAsStream("swipe_db.sql");
        BufferedReader sqlFileBufferedReader = new BufferedReader(new InputStreamReader(sqlFileInputStream));

        executeStatements(sqlFileBufferedReader);
    }

    private void executeStatements( BufferedReader br ) {
        String line;
        try {
            utx.begin();
            while ( (line = br.readLine()) != null ) {
                em.createNativeQuery(line).executeUpdate();
                logger.info("last line read successfully was: " + line);
            }
            utx.commit();
        } catch ( IOException | NotSupportedException | PersistenceException | SystemException | RollbackException | HeuristicMixedException | HeuristicRollbackException | SecurityException | IllegalStateException ex ) {
            logger.error(ex);
        }
    }

    private EntityManager getEntityManager() {
        return em;
    }

    private void setEm( EntityManager em ) {
        this.em = em;
    }

}
