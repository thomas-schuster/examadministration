package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.Country;
import de.hspf.swt.exam.administration.dao.HostUniversity;
import de.hspf.swt.exam.administration.dao.PersistenceTestProperties;
import de.hspf.swt.exam.administration.dao.Student;
import de.hspf.swt.exam.administration.dao.User;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author thomas.schuster
 */
public class LearningAgreementControllerTest {

    private static final Logger logger = LogManager.getLogger(LearningAgreementControllerTest.class);

    private Student student;
    private LearningAgreementController controllerInstance;

    private EntityManagerFactory emf;
    private EntityManager em;

    public LearningAgreementControllerTest() {
    }

    @After
    public void tearDown() {
        em.close();
        emf.close();
    }

    @Before
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("examAdminPU", PersistenceTestProperties.generatePersistenceProperties());
        em = emf.createEntityManager();
        em.getTransaction().begin();

        student = new Student("Bosch", "Hugo");
        student.setUserId("hugo@bosch.de");
        em.persist(student);
        Country usa = new Country("USA");
        em.persist(usa);
        Country slovenia = new Country("Slovenia");
        em.persist(slovenia);
        HostUniversity wyoming = new HostUniversity("University of Wyoming", "Laramie", usa);
        em.persist(wyoming);
        HostUniversity ljubljana = new HostUniversity("University of Ljubljana", "LJubljana", slovenia);
        em.persist(ljubljana);

        Application application1 = student.createApplication("WS 2019/2020", "WS 2019/2020", "C1", "Strengthen intercultural skills");
        Application application2 = student.createApplication("WS 2019/2020", "WS 2019/2020", "C1", "Strengthen intercultural skills");
        application1.createApplicationItem(wyoming, 1);
        application1.createApplicationItem(ljubljana, 2);
        application2.createApplicationItem(ljubljana, 1);
        application2.createApplicationItem(wyoming, 2);
        em.persist(application1);
        em.persist(application2);

        controllerInstance = new LearningAgreementController();
        em.getTransaction().commit();
    }

    private void check(int expectedNumberOfItems) {
        check(student, expectedNumberOfItems);
    }

    private void check(Student student, int expectedNumberOfItems) {
        List<ApplicationItem> result = controllerInstance.getApprovedApplicationItems(student);
        assertEquals(expectedNumberOfItems, result.size());
        logger.info("Number of approved Items = " + result.size());
        logger.info("Student in test: " + student.getFirstName() + " " + student.getLastName());
    }

    @Test
    public void testGetApprovedApplicationItemsNoApplications() {
        student = new Student("Boss", "Robert");
        logger.info("no Applications are create for this student");
        int expectedNumberOfItems = 0;
        check(expectedNumberOfItems);
    }

    @Test
    public void testGetApprovedApplicationItemsNoApplicationsDB() {
        em.getTransaction().begin();
        student = new Student("Boss", "Robert");
        student.setUserId("robert@boss.de");
        em.persist(student);
        logger.info("no Applications are created for this student");
        em.getTransaction().commit();
        int expectedNumberOfItems = 0;
        check(expectedNumberOfItems);
        Student s = (Student) em.find(User.class, student.getUserId());
        // was this student really stored in db?
        assertNotNull(s);
        assertEquals(s.getLastName(), student.getLastName());
    }

    @Test
    public void testGetApprovedApplicationItemsNoApplicationsItems() {
        logger.info("no ApplicationItems are set as approved");

        int expectedNumberOfItems = 0;
        check(expectedNumberOfItems);
    }

    @Test
    public void testGetApprovedApplicationItemsOneApplicationItem() {
        ApplicationItem item = student.getApplications().get(0).getApplicationItems().get(0);
        item.setAdmitted(true);
        em.merge(item);
        logger.info("one ApplicationItem is set as approved");

        int expectedNumberOfItems = 1;
        check(expectedNumberOfItems);
    }

    @Test
    public void testGetApprovedApplicationItemsTwoApplicationItemsDB() {
        student.getApplications().get(0).getApplicationItems().get(0).setAdmitted(true);
        student.getApplications().get(1).getApplicationItems().get(0).setAdmitted(true);
        logger.info("two ApplicationItems are set as approved");

        em.getTransaction().begin();
        em.persist(student);
        em.getTransaction().commit();

        Student s = (Student) em.find(User.class, student.getUserId());
        int expectedNumberOfItems = 2;
        check(s, expectedNumberOfItems);
    }

}
