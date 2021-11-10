package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.Country;
import de.hspf.swt.exam.administration.dao.HostUniverstiy;
import de.hspf.swt.exam.administration.dao.Student;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

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
        emf = Persistence.createEntityManagerFactory("integration-test");
        em = emf.createEntityManager();
        em.getTransaction().begin();

        student = new Student("Bosch", "Hugo");
        em.persist(student);
        Country usa = new Country("USA");
        em.persist(usa);
        Country slovenia = new Country("Slovenia");
        em.persist(slovenia);
        HostUniverstiy wyoming = new HostUniverstiy(usa, "University of Wyoming", "Laramie");
        em.persist(wyoming);
        HostUniverstiy ljubljana = new HostUniverstiy(slovenia, "University of Ljubljana", "LJubljana");
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
        em.persist(student);
        logger.info("no Applications are created for this student");
        em.getTransaction().commit();
        int expectedNumberOfItems = 0;
        check(expectedNumberOfItems);
        Student s = em.find(Student.class, student.getStudentID());
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

        Student s = em.find(Student.class, student.getStudentID());
        int expectedNumberOfItems = 2;
        check(s, expectedNumberOfItems);
    }

}
