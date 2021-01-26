package se.lexicon.course_manager_assignment.data.service.course;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.data.service.student.StudentService;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {CourseManager.class, CourseCollectionRepository.class, ModelToDto.class, StudentCollectionRepository.class})
public class CourseManagerTest {

    @Autowired
    private CourseService testObject;

    @Autowired
    private CourseDao courseDao;


    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(courseDao);
    }

    //Write your tests here


    @Test
    //Create two course forms (id will be overwritten when added to course repository, fault in code?).
    //Add them to course repository. Get all courses in repository. Check that list contains both
    //courses, and that id was assigned. Check size of course repository. Print repository,
    //HashSet format, for visual confirmation that they were added.
    void testCreateAndFindAll() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        List<CourseView> courseViews = testObject.findAll();
        Assertions.assertEquals(2,courseViews.size());
        Assertions.assertTrue(courseViews.contains(courseView1));
        Assertions.assertTrue(courseViews.contains(courseView2));
        System.out.println(courseDao.findAll());
    }

    @Test
    //Create three course forms and add to course repository. Two has same first name.
    //Search for a course by name. Check that course is in returned list.
    //Search for course with same first name. Check that courses are in returned list.
    void testSearchByCourseName() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CreateCourseForm courseForm3 = new CreateCourseForm(0,"Cooking advanced", LocalDate.of(2022, 8, 15), 8);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        CourseView courseView3 = testObject.create(courseForm3);
        List<CourseView> courseViews1 = testObject.searchByCourseName("Program");
        Assertions.assertTrue(courseViews1.contains(courseView1));
        List<CourseView> courseViews2 = testObject.searchByCourseName("cooking");
        Assertions.assertTrue(courseViews2.contains(courseView2));
        Assertions.assertTrue(courseViews2.contains(courseView3));
    }

    @Test
    //Find courses that ends before a certain date. Create three courses with different
    //start dates and lengths (weeks). Check a date where all three are in repository.
    //Check a date where one is in repository. Check a date where none is in repository.
    void testSearchByDateBefore() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CreateCourseForm courseForm3 = new CreateCourseForm(0,"Cooking advanced", LocalDate.of(2022, 8, 15), 8);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        CourseView courseView3 = testObject.create(courseForm3);
        List<CourseView> courseViews1 = testObject.searchByDateBefore(LocalDate.of(2022, 12, 31));
        List<CourseView> courseViews2 = testObject.searchByDateBefore(LocalDate.of(2021, 7, 15));
        List<CourseView> courseViews3 = testObject.searchByDateBefore(LocalDate.of(2021, 5, 5));
        Assertions.assertEquals(3, courseViews1.size());
        Assertions.assertEquals(1, courseViews2.size());
        Assertions.assertEquals(0, courseViews3.size());
        Assertions.assertTrue(courseViews1.contains(courseView1));
        Assertions.assertFalse(courseViews2.contains(courseView3));
    }

    @Test
    //Find courses that starts after a certain date. Create three courses with different
    //start dates. Check a date where all three are in repository.
    //Check a date where one is in repository. Check a date where none is in repository.
    void testSearchByDateAfter() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CreateCourseForm courseForm3 = new CreateCourseForm(0,"Cooking advanced", LocalDate.of(2022, 8, 15), 8);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        CourseView courseView3 = testObject.create(courseForm3);
        List<CourseView> courseViews1 = testObject.searchByDateAfter(LocalDate.of(2021, 4, 1));
        List<CourseView> courseViews2 = testObject.searchByDateAfter(LocalDate.of(2021, 7, 1));
        List<CourseView> courseViews3 = testObject.searchByDateAfter(LocalDate.of(2022, 8, 16));
        Assertions.assertEquals(3, courseViews1.size());
        Assertions.assertEquals(1, courseViews2.size());
        Assertions.assertEquals(0, courseViews3.size());
        Assertions.assertTrue(courseViews1.contains(courseView1));
        Assertions.assertFalse(courseViews2.contains(courseView2));
    }

    @Test
    //Create three course forms and add to repository. Search for a course by id.
    //Check that course returned has correct id and is expected course.
    void testFindById() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CreateCourseForm courseForm3 = new CreateCourseForm(0,"Cooking advanced", LocalDate.of(2022, 8, 15), 8);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        CourseView courseView3 = testObject.create(courseForm3);
        Assertions.assertEquals(courseView1, testObject.findById(1));
        Assertions.assertEquals(courseView2, testObject.findById(2));
        Assertions.assertEquals(courseView3, testObject.findById(3));
    }

    @Test
    //Add three courses to repository. Check that they were added. Remove one course
    //and check if removed. Try to remove the same course again, ie not in collection.
    //Print collection for visual confirmation.
    void testDeleteCourse() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CreateCourseForm courseForm3 = new CreateCourseForm(0,"Cooking advanced", LocalDate.of(2022, 8, 15), 8);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        CourseView courseView3 = testObject.create(courseForm3);
        Assertions.assertEquals(courseView1, testObject.findById(1));
        Assertions.assertEquals(courseView2, testObject.findById(2));
        Assertions.assertEquals(courseView3, testObject.findById(3));
        Assertions.assertTrue(testObject.deleteCourse(courseView1.getId()));
        Assertions.assertNull(testObject.findById(courseView1.getId()));
        Assertions.assertFalse(testObject.deleteCourse(courseView1.getId()));
        System.out.println(courseDao.findAll());
    }

    @Test
    void testAddStudentToCourse() {
        /*StudentView studentView1 = new StudentView(1, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        StudentView studentView2 = new StudentView(2, "Anna Lind", "a.mail@server.com", "myAddress 2");
        StudentView studentView3 = new StudentView(3, "Reine Anderson", "b.mail@server.com", "myAddress 3");
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        Assertions.assertTrue(testObject.addStudentToCourse(1,1));*/
    }

    @Test
    void testRemoveStudentFromCourse() {

    }

    @Test
    void testFindByStudentId() {

    }

    @Test
    //Add three courses to repository. Create an update form for a course by id.
    //Update a course's info by id. Check that updated course is in repository and old info is deleted.
    //Id and students are retained. Print repository for visual confirmation.
    void testUpdate() {
        CreateCourseForm courseForm1 = new CreateCourseForm(0,"Programming", LocalDate.of(2021, 5, 1), 5);
        CreateCourseForm courseForm2 = new CreateCourseForm(0,"Cooking", LocalDate.of(2021, 6, 1), 10);
        CreateCourseForm courseForm3 = new CreateCourseForm(0,"Cooking advanced", LocalDate.of(2022, 8, 15), 8);
        CourseView courseView1 = testObject.create(courseForm1);
        CourseView courseView2 = testObject.create(courseForm2);
        CourseView courseView3 = testObject.create(courseForm3);
        UpdateCourseForm updateCourseForm1 = new UpdateCourseForm(2,"Cooking beginner",LocalDate.of(2021, 7, 1), 15);
        CourseView updatedCourseView = testObject.update(updateCourseForm1);
        List<CourseView> courseViews = testObject.findAll();
        Assertions.assertTrue(courseViews.contains(updatedCourseView));
        Assertions.assertFalse(courseViews.contains(courseView2));
        System.out.println(courseDao.findAll());
    }

    @AfterEach
    void tearDown() {
        courseDao.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
