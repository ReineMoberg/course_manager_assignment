package se.lexicon.course_manager_assignment.data.service.student;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.dao.CourseCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentCollectionRepository;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.data.service.converter.ModelToDto;
import se.lexicon.course_manager_assignment.dto.forms.CreateStudentForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateStudentForm;
import se.lexicon.course_manager_assignment.dto.views.StudentView;



import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {StudentManager.class, CourseCollectionRepository.class, StudentCollectionRepository.class, ModelToDto.class})
public class StudentManagerTest {

    @Autowired
    private StudentService testObject;
    @Autowired
    private StudentDao studentDao;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
        assertNotNull(studentDao);
    }

    //Write your tests here


    @Test
    //Create two student forms (id will be overwritten when added to student repository, fault in code?).
    //Add them to student repository. Get all students in repository. Check that list contains both
    //students, and that id was assigned. Check size of student repository. Print repository,
    //HashSet format, for visual confirmation that they were added.
    void testCreateAndFindAll() {
        CreateStudentForm studentForm1 = new CreateStudentForm(0, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        CreateStudentForm studentForm2 = new CreateStudentForm(0, "Anna Lind", "a.mail@server.com", "myAddress 2");
        StudentView studentView1 = testObject.create(studentForm1);
        StudentView studentView2 = testObject.create(studentForm2);
        List<StudentView> studentViews1 = testObject.findAll();
        Assertions.assertTrue(studentViews1.contains(studentView1));
        Assertions.assertTrue(studentViews1.contains(studentView2));
        Assertions.assertEquals(1, studentView1.getId());
        Assertions.assertEquals(2, studentView2.getId());
        Assertions.assertEquals(2, studentViews1.size());
        System.out.println(studentDao.findAll());
    }

    @Test
    //Create three student forms and add to student repository. Search for a student by id.
    //Check that student returned has correct id and is expected student.
    void testFindById() {
        CreateStudentForm studentForm1 = new CreateStudentForm(0, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        CreateStudentForm studentForm2 = new CreateStudentForm(0, "Anna Lind", "a.mail@server.com", "myAddress 2");
        CreateStudentForm studentForm3 = new CreateStudentForm(0, "Reine Anderson", "b.mail@server.com", "myAddress 3");
        StudentView studentView1 = testObject.create(studentForm1);
        StudentView studentView2 = testObject.create(studentForm2);
        StudentView studentView3 = testObject.create(studentForm3);
        StudentView resultView = testObject.findById(2);
        Assertions.assertEquals(2, studentView2.getId());
        Assertions.assertEquals(studentView2, resultView);
    }

    @Test
    //Create three student forms and add to student repository. Search for a student by email.
    //Check that correct student is returned.
    void testSearchByEmail() {
        CreateStudentForm studentForm1 = new CreateStudentForm(0, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        CreateStudentForm studentForm2 = new CreateStudentForm(0, "Anna Lind", "a.mail@server.com", "myAddress 2");
        CreateStudentForm studentForm3 = new CreateStudentForm(0, "Reine Anderson", "b.mail@server.com", "myAddress 3");
        StudentView studentView1 = testObject.create(studentForm1);
        StudentView studentView2 = testObject.create(studentForm2);
        StudentView studentView3 = testObject.create(studentForm3);
        StudentView resultView = testObject.searchByEmail("e.mail@server.com");
        Assertions.assertEquals(studentView1, resultView);
    }

    @Test
    //Create three student forms and add to student repository. Two has same first name.
    //Search for a student by name. Check that student is in returned list.
    //Search for students with same first name. Check that students are in returned list.
    void testSearchByName() {
        CreateStudentForm studentForm1 = new CreateStudentForm(0, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        CreateStudentForm studentForm2 = new CreateStudentForm(0, "Anna Lind", "a.mail@server.com", "myAddress 2");
        CreateStudentForm studentForm3 = new CreateStudentForm(0, "Reine Anderson", "b.mail@server.com", "myAddress 3");
        StudentView studentView1 = testObject.create(studentForm1);
        StudentView studentView2 = testObject.create(studentForm2);
        StudentView studentView3 = testObject.create(studentForm3);
        List<StudentView> resultViews1 = testObject.searchByName("Anna");
        Assertions.assertTrue(resultViews1.contains(studentView2));
        List<StudentView> resultViews2 = testObject.searchByName("Reine");
        Assertions.assertTrue(resultViews2.contains(studentView1));
        Assertions.assertTrue(resultViews2.contains(studentView3));
    }

    @Test
    //Create three student forms and add to student repository. Check that they are in repository.
    //Delete one student and check if deleted. Try to delete same student again, ie not in repository.
    void testDeleteStudent() {
        CreateStudentForm studentForm1 = new CreateStudentForm(0, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        CreateStudentForm studentForm2 = new CreateStudentForm(0, "Anna Lind", "a.mail@server.com", "myAddress 2");
        CreateStudentForm studentForm3 = new CreateStudentForm(0, "Reine Anderson", "b.mail@server.com", "myAddress 3");
        StudentView studentView1 = testObject.create(studentForm1);
        StudentView studentView2 = testObject.create(studentForm2);
        StudentView studentView3 = testObject.create(studentForm3);
        Assertions.assertEquals(studentView1, testObject.findById(1));
        Assertions.assertEquals(studentView2, testObject.findById(2));
        Assertions.assertTrue(testObject.deleteStudent(2));
        Assertions.assertNull(testObject.findById(2));
        Assertions.assertFalse(testObject.deleteStudent(2));
    }

    @Test
    //Create three student forms and add to student repository. Create an update form for a student by id.
    //Update a student's info by id. Check that updated student is in repository and old info is deleted.
    //Id is retained. Print repository for visual confirmation.
    void testUpdate() {
        CreateStudentForm studentForm1 = new CreateStudentForm(0, "Reine Moberg", "e.mail@server.com", "myAdress 1");
        CreateStudentForm studentForm2 = new CreateStudentForm(0, "Anna Lind", "a.mail@server.com", "myAddress 2");
        CreateStudentForm studentForm3 = new CreateStudentForm(0, "Reine Anderson", "b.mail@server.com", "myAddress 3");
        StudentView studentView1 = testObject.create(studentForm1);
        StudentView studentView2 = testObject.create(studentForm2);
        StudentView studentView3 = testObject.create(studentForm3);
        UpdateStudentForm updateStudentForm1 = new UpdateStudentForm(1, "Reine Moberg", "e.mail@newserver.com", "myNewAddress 1");
        StudentView updatedStudentView = testObject.update(updateStudentForm1);
        List<StudentView> studentViews = testObject.findAll();
        Assertions.assertTrue(studentViews.contains(updatedStudentView));
        Assertions.assertFalse(studentViews.contains(studentView1));
        System.out.println(studentDao.findAll());
    }

    @AfterEach
    void tearDown() {
        StudentSequencer.setStudentSequencer(0);
        studentDao.clear();
    }
}
