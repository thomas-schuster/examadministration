package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.ApplicationItem;
import de.hspf.swt.exam.administration.dao.Country;
import de.hspf.swt.exam.administration.dao.Course;
import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HomeUniversity;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.HostUniversity;
import de.hspf.swt.exam.administration.dao.LearningAgreement;
import de.hspf.swt.exam.administration.dao.LearningAgreementItem;
import de.hspf.swt.exam.administration.dao.Student;
import de.hspf.swt.exam.administration.dao.StudyProgram;
import de.hspf.swt.exam.administration.dao.University;
import de.hspf.swt.exam.administration.dao.User;
import de.hspf.swt.exam.administration.dao.UserGroup;
import de.hspf.swt.exam.administration.dao.facade.ApplicationFacade;
import de.hspf.swt.exam.administration.dao.facade.ApplicationItemFacade;
import de.hspf.swt.exam.administration.dao.facade.CountryFacade;
import de.hspf.swt.exam.administration.dao.facade.HomeCourseFacade;
import de.hspf.swt.exam.administration.dao.facade.HomeUniversityFacade;
import de.hspf.swt.exam.administration.dao.facade.HostCourseFacade;
import de.hspf.swt.exam.administration.dao.facade.HostUniversityFacade;
import de.hspf.swt.exam.administration.dao.facade.StudentFacade;
import de.hspf.swt.exam.administration.dao.facade.StudyProgramFacade;
import de.hspf.swt.exam.administration.dao.facade.UserFacade;
import de.hspf.swt.exam.administration.dao.util.DatabaseTool;
import de.hspf.swt.exam.administration.exceptions.MoreThanOneHomeCourseWarning;
import de.hspf.swt.exam.administration.exceptions.MoreThanOneHostCourseWarning;
import de.hspf.swt.exam.administration.exceptions.OnlyOneHostCourseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author thomas.schuster
 */
@Singleton
@Startup
public class ApplicationStartupController {

    private static final Logger logger = LogManager.getLogger(ApplicationStartupController.class);

    @EJB
    ApplicationFacade applicationManager;
    @EJB
    ApplicationItemFacade applicationItemManager;
    @EJB
    CountryFacade countryManager;
    @EJB
    HomeCourseFacade homeCourseManager;
    @EJB
    HomeUniversityFacade homeUniversityManager;
    @EJB
    HostCourseFacade hostCourseManager;
    @EJB
    HostUniversityFacade hostUniversityManager;
    @EJB
    StudyProgramFacade sprogramManager;
    @EJB
    StudentFacade studentManager;
    @EJB
    UserFacade userManager;
    @EJB
    DatabaseTool dataTooling;
   

    @PostConstruct
    public void checkDatabase() {
        boolean cleanStart = false;
        List<User> users = userManager.findAll();
        if ( users.isEmpty() == true ) {
            initializeDatabase();
        } else if ( cleanStart == true ) {
            dataTooling.swipeDatabase();
            initializeDatabase();
        }
    }

    public void initializeDatabase() {
        logger.info("create sample data");
        Country germany = insertCountry("Germany");
        HomeUniversity irgendwo = insertHomeUniversity("Irgendwo", "Hochschule fuer Wirtschaft", germany);
        StudyProgram studyProgram = createHomeStudyProgram(irgendwo);
        homeUniversityManager.create(irgendwo);

        Country slovenia = insertCountry("Slovenia");
        HostUniversity lubljana = insertHostUniversity("Lubljana", "University of Ljubljana", slovenia);

        Country usa = insertCountry("USA");
        HostUniversity laramie = insertHostUniversity("Laramie", "University of Wyoming", usa);
        HostUniversity maryland = insertHostUniversity("College Park", "University of Maryland", usa);
        Collection<HostCourse> hostCourses = createCourseData();
        hostCourses.forEach(lubljana::addHostCourse);
        hostUniversityManager.edit(lubljana);
        hostCourses.forEach(laramie::addHostCourse);
        hostUniversityManager.edit(laramie);
        hostCourses.forEach(maryland::addHostCourse);
        hostUniversityManager.edit(maryland);

        Student student = createStudentUser();
        student.setHomeUniversity(irgendwo);
        student.setStudyProgram(studyProgram);
        
        Application applicationLaramie = createApplication(student, laramie, lubljana);

        LearningAgreement agreement = applicationLaramie.getApplicationItems().get(0).getStudent().createLearningAgreement(applicationLaramie.getApplicationItems().get(0));
        LearningAgreementItem litem = agreement.createLearningAgreementItem();
        try {
            litem.addHomeCourse(studyProgram.getCourses().get(0), true);
            litem.addHostCourse(hostCourses.iterator().next(), true);
            litem.addHomeCourse(studyProgram.getCourses().get(1), true);
        } catch ( OnlyOneHostCourseException | MoreThanOneHostCourseWarning | MoreThanOneHomeCourseWarning ex ) {
            logger.error(ex);
        }
        
        ApplicationItem appItem = applicationLaramie.getApprovedApplicationItems().get(0);
        appItem.setLearningAgreement(agreement);
        applicationItemManager.edit(appItem);
        

        createApplication(student, lubljana, laramie);
        studentManager.edit(student);
        logger.info("finished sample data creation");
    }

    private Application createApplication( Student student, HostUniversity laramie, HostUniversity lubljana ) {
        Application application = student.createApplication("WS 2020/2021", "WS 2020/2021", "C1", "Strengthen intercultural skills");
        application.createApplicationItem(laramie, 1);
        application.createApplicationItem(lubljana, 2);

        application.getApplicationItems().get(0).setAdmitted(true);

        applicationManager.create(application);
        return application;
    }

    private Collection<HostCourse> createCourseData() {
        ArrayList<HostCourse> hostcourses = new ArrayList<>();
        hostcourses.add(insertHostCourse("Mgt3110", "Business Ethics", 3d));
        hostcourses.add(insertHostCourse("Mgt4800", "Business Strategy and Policy", 3d));
        hostcourses.add(insertHostCourse("Mgt4530", "Business Plan Development", 3d));
        hostcourses.add(insertHostCourse("DSCI3210", "Production and Operations Management", 3d));
        hostcourses.add(insertHostCourse("Mgt4445", "Managing Risk and Knowledge", 3d));
        hostcourses.add(insertHostCourse("", "International Marketing", 6d));
        hostcourses.add(insertHostCourse("", "Operations Management", 6d));
        hostcourses.add(insertHostCourse("", "Sustainable Tourism", 6d));
        hostcourses.add(insertHostCourse("", "Managing People at Work", 6d));
        hostcourses.add(insertHostCourse("", "Operations Management", 3d));
        hostcourses.add(insertHostCourse("", "Operations Management", 3d));
        return hostcourses;
    }

    private StudyProgram createHomeStudyProgram( HomeUniversity irgendwo ) {
        StudyProgram studyProgram = irgendwo.createStudyProgram("Purchase and Logistics");
        studyProgram.addCourse(insertHomeCourse("ESR4011", "Busines Ethics 1", 3d));
        studyProgram.addCourse(insertHomeCourse("ESR4012", "Busines Ethics 2", 3d));
        studyProgram.addCourse(insertHomeCourse("GMT3021", "Operations Management", 7d));
        studyProgram.addCourse(insertHomeCourse("GMT3013", "Strategic Management", 3d));
        studyProgram.addCourse(insertHomeCourse("GMT3022", "International Trade Operations", 6d));
        studyProgram.addCourse(insertHomeCourse("GMT4101", "Management Seminar", 5d));
        sprogramManager.create(studyProgram);
        return studyProgram;
    }

    private Student createStudentUser() {
        Student student = new Student("Bosch", "Hugo");
        student.setUserId("hugo@bosch.cc");
        student.setPassword("modisch");
        UserGroup group = new UserGroup();
        group.setGroupName("student");
        student.addUserGroup(group);
        studentManager.create(student);
        return student;
    }

    private Country insertCountry( String countryName ) {
        Country country = new Country();
        country.setCountryName(countryName);
        countryManager.create(country);
        return country;
    }

    private HomeCourse insertHomeCourse( String courseId, String title, Double ects ) {
        HomeCourse course = new HomeCourse();
        setCourseProperties(course, courseId, title, ects);
        homeCourseManager.create(course);
        return course;
    }

    private HomeUniversity insertHomeUniversity( String city, String name, Country country ) {
        HomeUniversity university = new HomeUniversity();
        setUniversityProperties(university, city, name, country);
        homeUniversityManager.create(university);
        return university;
    }

    private HostCourse insertHostCourse( String courseId, String title, Double ects ) {
        HostCourse course = new HostCourse();
        setCourseProperties(course, courseId, title, ects);
        hostCourseManager.create(course);
        return course;
    }

    private HostUniversity insertHostUniversity( String city, String name, Country country ) {
        HostUniversity university = new HostUniversity();
        setUniversityProperties(university, city, name, country);
        hostUniversityManager.create(university);
        return university;
    }

    private <T extends Course> void setCourseProperties( T t, String courseId, String title, Double ects ) {
        t.setCourseId(courseId);
        t.setCourseTitle(title);
        t.setEcts(ects);
    }

    private <T extends University> void setUniversityProperties( T t, String city, String name, Country country ) {
        t.setCity(city);
        t.setUniversityName(name);
        t.setCountry(country);
    }
    

}
