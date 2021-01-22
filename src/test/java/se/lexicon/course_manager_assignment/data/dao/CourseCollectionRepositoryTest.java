package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {CourseCollectionRepository.class})
public class CourseCollectionRepositoryTest {

    @Autowired
    private CourseDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    //Write your tests here


    @Test
    //Create two course and add to collection in CourseCollectionRepository.
    //New id is generated automatically. Check if courses are added successfully
    //by checking if they exist in collection. Print for visual confirmation.
    void testCreateCourseAndFindAll() {
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Assertions.assertTrue(testObject.findAll().contains(course1));
        Assertions.assertTrue(testObject.findAll().contains(course2));
        System.out.println(course1);
        System.out.println(course2);
        System.out.println(testObject.findAll());
    }

    @Test
    //Check that collection is empty. Add two courses. Check that collection consists
    //of two courses.
    void testFindAll() {
        Assertions.assertEquals(0, testObject.findAll().size());
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Assertions.assertEquals(2, testObject.findAll().size());
    }

    @Test
    //Add two courses to collection. Search by id and check that correct course is returned.
    //Search for an id that doesn't exist and check that null, ie empty, is returned.
    void testFindById() {
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Assertions.assertEquals(course1, testObject.findById(1));
        Assertions.assertEquals(course2, testObject.findById(2));
        Assertions.assertNull(testObject.findById(3));
    }

    @Test
    //Add three courses to collection. Two of them with the same name.
    //Find a collection of courses by searching part of name, case insensitive.
    //Search a course not in collection.
    void testFindByNameContains() {
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Course course3 = testObject.createCourse("Cooking", LocalDate.of(2022, 8, 15), 8);
        Collection<Course> result1 = testObject.findByNameContains("programming");
        Collection<Course> result2 = testObject.findByNameContains("Cooking");
        Collection<Course> result3 = testObject.findByNameContains("Program");
        Collection<Course> result4 = testObject.findByNameContains("Knitting");
        Collection<Course> compare1 = new HashSet<>();
        compare1.add(course1);
        Collection<Course> compare2 = new HashSet<>();
        compare2.add(course2);
        compare2.add(course3);
        Collection<Course> compare3 = new HashSet<>();
        compare3.add(course1);
        Collection<Course> compare4 = new HashSet<>();
        Assertions.assertEquals(compare1, result1);
        Assertions.assertEquals(compare2, result2);
        Assertions.assertEquals(compare3, result3);
        Assertions.assertEquals(compare4, result4);
    }

    @Test
    //Add two courses to collection. Check that they were added. Remove one course
    //and check if removed. Try to remove the same course again, ie not in collection.
    //Print collection for visual confirmation.
    void testRemoveCourse() {
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Assertions.assertEquals(course1, testObject.findById(1));
        Assertions.assertEquals(course2, testObject.findById(2));
        Assertions.assertTrue(testObject.removeCourse(course1));
        Assertions.assertNull(testObject.findById(1));
        Assertions.assertFalse(testObject.removeCourse(course1));
        System.out.println(testObject.findAll());
    }

    @Test
    //Find courses that ends before a certain date. Create three courses with different
    //start dates and lengths (weeks). Check a date where all three are in collection.
    //Check a date where one is in collection. Check a date where none is in collection.
    void testFindByDateBefore() {
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Course course3 = testObject.createCourse("Cooking", LocalDate.of(2022, 8, 15), 8);
        Collection<Course> result1 = testObject.findByDateBefore(LocalDate.of(2022, 12, 31));
        Collection<Course> result2 = testObject.findByDateBefore(LocalDate.of(2021, 7, 15));
        Collection<Course> result3 = testObject.findByDateBefore(LocalDate.of(2021, 5, 5));
        Assertions.assertEquals(3, result1.size());
        Assertions.assertEquals(1, result2.size());
        Assertions.assertEquals(0, result3.size());
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    //Find courses that starts after a certain date. Create three courses with different
    //start dates. Check a date where all three are in collection.
    //Check a date where one is in collection. Check a date where none is in collection.
    void testFindByDateAfter() {
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Course course3 = testObject.createCourse("Cooking", LocalDate.of(2022, 8, 15), 8);
        Collection<Course> result1 = testObject.findByDateAfter(LocalDate.of(2021, 4, 1));
        Collection<Course> result2 = testObject.findByDateAfter(LocalDate.of(2021, 7, 1));
        Collection<Course> result3 = testObject.findByDateAfter(LocalDate.of(2022, 8, 16));
        Assertions.assertEquals(3, result1.size());
        Assertions.assertEquals(1, result2.size());
        Assertions.assertEquals(0, result3.size());
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    //Create three students. Add to two collections where one student is in both collections.
    //Create two courses and add a student collection to each. Check, by student id, that both courses
    //are returned for student in both courses. Check that one course each for the other two.
    void testFindByStudentId() {
        StudentSequencer.setStudentSequencer(0);
        Student student1 = new Student("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = new Student("Anna Lind", "a.mail@server.com", "myAddress 2");
        Student student3 = new Student("Kalle Carlsson", "B.Mail@server.com", "myAddress3");
        Collection<Student> studentCollection1 = new HashSet<>();
        studentCollection1.add(student1);
        studentCollection1.add(student2);
        Collection<Student> studentCollection2 = new HashSet<>();
        studentCollection2.add(student2);
        studentCollection2.add(student3);
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        course1.setStudents(studentCollection1);
        course2.setStudents(studentCollection2);
        Collection<Course> result1 = testObject.findByStudentId(1);
        Collection<Course> result2 = testObject.findByStudentId(2);
        Collection<Course> result3 = testObject.findByStudentId(3);
        Assertions.assertEquals(1, result1.size());
        Assertions.assertEquals(2, result2.size());
        Assertions.assertEquals(1, result3.size());
        System.out.println(result1);
        System.out.println(result2);
        System.out.println(result3);
    }

    @Test
    //Check that collection is empty. Add two courses. Check that collection consists
    //of two courses. Clear collection and check if empty.
    void testClear() {
        Assertions.assertEquals(0, testObject.findAll().size());
        Course course1 = testObject.createCourse("Programming", LocalDate.of(2021, 5, 1), 5);
        Course course2 = testObject.createCourse("Cooking", LocalDate.of(2021, 6, 1), 10);
        Assertions.assertEquals(2, testObject.findAll().size());
        testObject.clear();
        Assertions.assertEquals(0, testObject.findAll().size());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        CourseSequencer.setCourseSequencer(0);
    }
}
