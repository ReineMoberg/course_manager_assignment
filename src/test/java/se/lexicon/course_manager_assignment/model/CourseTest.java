package se.lexicon.course_manager_assignment.model;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;

public class CourseTest {

    @Test
    void testSetGetId() {
        CourseSequencer.setCourseSequencer(0);
        Course course1 = new Course();
        Course course2 = new Course(5);
        Assertions.assertEquals(1, course1.getId());
        Assertions.assertEquals(5, course2.getId());
    }

    @Test
    //Create two unique id students. Create one more student with same id.
    //Only the unique students should be enrolled, the third should not be added.
    //Print course for visual confirmation
    void testEnrollStudent() {
        CourseSequencer.setCourseSequencer(0);
        StudentSequencer.setStudentSequencer(0);
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student(2);
        Course course1 = new Course();
        boolean success1 = course1.enrollStudent(student1);
        boolean success2 = course1.enrollStudent(student2);
        boolean success3 = course1.enrollStudent(student3);
        Assertions.assertTrue(success1);
        Assertions.assertTrue(success2);
        Assertions.assertFalse(success3);
        System.out.println(course1);
    }

    @Test
    //Create three unique id students. Enroll two of them in course.
    //Remove one student. Remove a student not enrolled in course.
    //Print for visual confirmation
    void testUnenrollStudent() {
        CourseSequencer.setCourseSequencer(0);
        StudentSequencer.setStudentSequencer(0);
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student();
        Course course1 = new Course();
        course1.enrollStudent(student1);
        course1.enrollStudent(student2);
        boolean success1 = course1.unenrollStudent(student1);
        boolean success3 = course1.unenrollStudent(student3);
        Assertions.assertTrue(success1);
        Assertions.assertFalse(success3);
        System.out.println(course1);
    }

    @Test
    //Create two students and add them to a collection of students.
    //Set collection in course. Get collection of students in course
    //and confirm they are all the same
    void testSetGetStudents() {
        CourseSequencer.setCourseSequencer(0);
        StudentSequencer.setStudentSequencer(0);
        Collection<Student> testStudents1 = new HashSet<>();
        Collection<Student> testStudents2;
        testStudents1.add(new Student());
        testStudents1.add(new Student());
        Course course1 = new Course();
        course1.setStudents(testStudents1);
        testStudents2 = course1.getStudents();
        Assertions.assertEquals(testStudents1, course1.getStudents());
        Assertions.assertEquals(testStudents1, testStudents2);
        System.out.println(testStudents1);
        System.out.println(testStudents2);
        System.out.println(course1.getStudents());
    }

    @Test
    void testSetGetCourseName() {
        Course course1 = new Course();
        course1.setCourseName("Programming");
        Assertions.assertEquals("Programming", course1.getCourseName());
    }

    @Test
    void testSetGetStartDate() {
        Course course1 = new Course();
        course1.setStartDate(LocalDate.of(2021, 3, 25));
        Assertions.assertEquals(LocalDate.of(2021, 3, 25), course1.getStartDate());
        System.out.println(course1.getStartDate());
    }

    @Test
    void testSetGetWeekDuration() {
        Course course1 = new Course();
        course1.setWeekDuration(20);
        Assertions.assertEquals(20, course1.getWeekDuration());
    }
}
