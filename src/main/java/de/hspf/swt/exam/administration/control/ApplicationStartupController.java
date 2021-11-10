package de.hspf.swt.exam.administration.control;

import de.hspf.swt.exam.administration.dao.Application;
import de.hspf.swt.exam.administration.dao.Country;
import de.hspf.swt.exam.administration.dao.Course;
import de.hspf.swt.exam.administration.dao.HomeCourse;
import de.hspf.swt.exam.administration.dao.HomeUniversity;
import de.hspf.swt.exam.administration.dao.HostCourse;
import de.hspf.swt.exam.administration.dao.HostUniversity;
import de.hspf.swt.exam.administration.dao.Student;
import de.hspf.swt.exam.administration.dao.StudyProgram;
import de.hspf.swt.exam.administration.dao.University;
import de.hspf.swt.exam.administration.dao.User;
import de.hspf.swt.exam.administration.dao.facade.ApplicationFacade;
import de.hspf.swt.exam.administration.dao.facade.CountryFacade;
import de.hspf.swt.exam.administration.dao.facade.HomeCourseFacade;
import de.hspf.swt.exam.administration.dao.facade.HomeUniversityFacade;
import de.hspf.swt.exam.administration.dao.facade.HostCourseFacade;
import de.hspf.swt.exam.administration.dao.facade.HostUniversityFacade;
import de.hspf.swt.exam.administration.dao.facade.StudentFacade;
import de.hspf.swt.exam.administration.dao.facade.StudyProgramFacade;
import de.hspf.swt.exam.administration.dao.facade.UserFacade;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;

/**
 *
 * @author thomas.schuster
 */
@Singleton
@Startup
public class ApplicationStartupController {

    @EJB
    ApplicationFacade applicationManager;
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

    @PostConstruct
    public void checkDatabase() {
        List<User> users = userManager.findAll();
        if (users.isEmpty() == true) {
            initializeDatabase();
        }
    }
    public void initializeDatabase() {
        Country germany = insertCountry("Germany");
        HomeUniversity irgendwo = insertHomeUniversity("Irgendwo", "Hochschule für Wirtschaft", germany);
        createHomeStudyProgram(irgendwo);
        homeUniversityManager.edit(irgendwo);

        Country slovenia = insertCountry("Slovenia");
        HostUniversity lubljana = insertHostUniversity("Lubljana", "University of Ljubljana", slovenia);

        Country usa = insertCountry("USA");
        HostUniversity laramie = insertHostUniversity("Laramie", "University of Wyoming", usa);

        Student student = createStudentUser();
        createApplication(student, laramie, lubljana);
        createApplication(student, lubljana, laramie);
        studentManager.edit(student);

        createCourseData();
    }

    private void createApplication(Student student, HostUniversity laramie, HostUniversity lubljana) {
        Application application1 = student.createApplication("WS 2019/2020", "WS 2019/2020", "C1", "Strengthen intercultural skills");
        application1.createApplicationItem(laramie, 1);
        application1.createApplicationItem(lubljana, 2);
        applicationManager.create(application1);
    }

    private void createCourseData() {
        insertHostCourse("Mgt3110", "Business Ethics", 3d);
        insertHostCourse("Mgt4800", "Business Strategy and Policy", 3d);
        insertHostCourse("Mgt4530", "Business Plan Development", 3d);
        insertHostCourse("DSCI3210", "Production and Operations Management", 3d);
        insertHostCourse("Mgt4445", "Managing Risk and Knowledge", 3d);
        insertHostCourse("", "International Marketing", 6d);
        insertHostCourse("", "Operations Management", 6d);
        insertHostCourse("", "Sustainable Tourism", 6d);
        insertHostCourse("", "Managing People at Work", 6d);
        insertHostCourse("", "Operations Management", 3d);
        insertHostCourse("", "Operations Management", 3d);
    }

    private void createHomeStudyProgram(HomeUniversity irgendwo) {
        StudyProgram studyProgram = irgendwo.createStudyProgram("Purchase and Logistics");
        studyProgram.addCourse(insertHomeCourse("ESR4011", "Busines Ethics 1", 3d));
        studyProgram.addCourse(insertHomeCourse("ESR4012", "Busines Ethics 2", 3d));
        studyProgram.addCourse(insertHomeCourse("GMT3021", "Operations Management", 7d));
        studyProgram.addCourse(insertHomeCourse("GMT3013", "Strategic Management", 3d));
        studyProgram.addCourse(insertHomeCourse("GMT3022", "International Trade Operations", 6d));
        studyProgram.addCourse(insertHomeCourse("GMT4101", "Management Seminar", 5d));
        sprogramManager.create(studyProgram);
    }

    private Student createStudentUser() {
        Student student = new Student("Bosch", "Hugo");
        student.setUserId("hugo@bosch.cc");
        studentManager.create(student);
        User studentUser = new User();
        studentUser.setUserId(student.getUserId());
        studentUser.setPassword("modisch");
        return student;
    }

    private Country insertCountry(String countryName) {
        Country country = new Country();
        country.setCountryName(countryName);
        countryManager.create(country);
        return country;
    }

    private HomeCourse insertHomeCourse(String courseId, String title, Double ects) {
        HomeCourse course = new HomeCourse();
        setCourseProperties(course, courseId, title, ects);
        homeCourseManager.create(course);
        return course;
    }

    private HomeUniversity insertHomeUniversity(String city, String name, Country country) {
        HomeUniversity university = new HomeUniversity();
        setUniversityProperties(university, city, name, country);
        homeUniversityManager.create(university);
        return university;
    }

    private HostCourse insertHostCourse(String courseId, String title, Double ects) {
        HostCourse course = new HostCourse();
        setCourseProperties(course, courseId, title, ects);
        hostCourseManager.create(course);
        return course;
    }

    private HostUniversity insertHostUniversity(String city, String name, Country country) {
        HostUniversity university = new HostUniversity();
        setUniversityProperties(university, city, name, country);
        hostUniversityManager.create(university);
        return university;
    }

    private <T extends Course> void setCourseProperties(T t, String courseId, String title, Double ects) {
        t.setCourseId(courseId);
        t.setCourseTitle(title);
        t.setEcts(ects);
    }

    private <T extends University> void setUniversityProperties(T t, String city, String name, Country country) {
        t.setCity(city);
        t.setUniversityName(name);
        t.setCountry(country);
    }

}
