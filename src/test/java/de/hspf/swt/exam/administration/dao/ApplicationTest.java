package de.hspf.swt.exam.administration.dao;

import de.hspf.swt.exam.administration.dao.facade.ApplicationFacade;
import java.util.ArrayList;
import javax.persistence.EntityManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 *
 * @author thomas.schuster
 */
@RunWith(MockitoJUnitRunner.StrictStubs.class)
public class ApplicationTest {

    private ApplicationFacade appFacade;
    private Application application;
    private static final Logger logger = LogManager.getLogger(ApplicationTest.class);

    @Mock
    private EntityManager entityManager;

    public ApplicationTest() {
    }

    @Before
    public void setUp() {
        appFacade = new ApplicationFacade();
        appFacade.setEm(entityManager);

        Country usa = new Country("USA");
        Country slovenia = new Country("Slovenia");
        HostUniversity wyoming = new HostUniversity("University of Wyoming", "Laramie", usa);
        HostUniversity ljubljana = new HostUniversity("University of Ljubljana", "LJubljana", slovenia);

        application = new Application("WS 2019/2020", "WS 2019/2020", "C1", "Strengthen intercultural skills", null);
        application.createApplicationItem(wyoming, 1);
        application.createApplicationItem(ljubljana, 2);
    }

    @Test
    public void testStorage() {
        Application instance = new Application();
        logger.info("Id is:" + instance.getId());
        instance.setLastSemester("sommer");
        appFacade.create(instance);
        logger.info("Id is:" + instance.getId());
        assertNotNull(instance.getId());
    }

    @Test
    public void testGetApprovedApplicationItems() {
        logger.info("getApprovedApplicationItemsWithOne");
        application.getApplicationItems().get(0).setAdmitted(true);
        int expResult = 1;
        ArrayList<ApplicationItem> result = application.getApprovedApplicationItems();
        assertEquals(expResult, result.size());
        logger.info("Number of expected approved items: " + result.size());
    }

    @Test
    public void testNoApprovedApplicationItems() {
        logger.info("getApprovedApplicationItemsWithZero");
        int expResult = 0;
        ArrayList<ApplicationItem> result = application.getApprovedApplicationItems();
        assertEquals(expResult, result.size());
        logger.info("Number of expected approved items: " + result.size());
    }
}
