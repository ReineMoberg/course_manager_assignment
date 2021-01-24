package se.lexicon.course_manager_assignment.data.service.converter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = {ModelToDto.class})
public class ModelToDtoTest {

    @Autowired
    private Converters testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertNotNull(testObject);
    }

    //write your tests here


    @Test
    //Create Student object. Convert to StudentView object with Student object.
    //Compare values in objects that they are same values. Check that null
    //is returned if Student is null.
    void testStudentToStudentView() {
        StudentSequencer.setStudentSequencer(0);
        Student student1 = new Student(StudentSequencer.nextStudentId(), "Reine Moberg", "e.mail@server.com", "myAddress 1");
        StudentView studentView1 = testObject.studentToStudentView(student1);
        Assertions.assertTrue(student1.getId() == studentView1.getId() &&
                student1.getName().equals(studentView1.getName()) &&
                student1.getAddress().equals(studentView1.getAddress()) &&
                student1.getEmail().equals(studentView1.getEmail()));
        Student student2 = null;
        StudentView studentView2 = testObject.studentToStudentView(student2);
        Assertions.assertNull(studentView2);
    }

    @Test
    //Create two students. Add them to Hashset. Convert set to a List of StudentView.
    //Convert each student to StudentView object. Check if they are in List,
    //and check List size. Check that null is returned if set is null.
    void testStudentsToStudentViews() {
        StudentSequencer.setStudentSequencer(0);
        Collection<Student> students = new HashSet<>();
        Student student1 = new Student(StudentSequencer.nextStudentId(), "Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = new Student(StudentSequencer.nextStudentId(), "Anna Lind", "a.mail@server.com", "myAddress 2");
        students.add(student1);
        students.add(student2);
        List<StudentView> studentViews1 = testObject.studentsToStudentViews(students);
        StudentView studentView1 = testObject.studentToStudentView(student1);
        StudentView studentView2 = testObject.studentToStudentView(student2);
        Assertions.assertTrue(studentViews1.contains(studentView1));
        Assertions.assertTrue(studentViews1.contains(studentView2));
        Assertions.assertEquals(2, studentViews1.size());
        Collection<Student> students2 = null;
        List<StudentView> studentViews2 = testObject.studentsToStudentViews(students2);
        Assertions.assertNull(studentViews2);
    }

    @Test
    //Create two students. Add them to Hashset. Create a course with student HashSet.
    //Convert course to CourseView object. Convert student set to a List of StudentView.
    //Compare values in objects that they are same values. Check that null is returned
    //if course is null.
    void testCourseToCourseView() {
        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);
        Collection<Student> students = new HashSet<>();
        Student student1 = new Student(StudentSequencer.nextStudentId(), "Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = new Student(StudentSequencer.nextStudentId(), "Anna Lind", "a.mail@server.com", "myAddress 2");
        students.add(student1);
        students.add(student2);
        Course course1 = new Course(CourseSequencer.nextCourseId(), "Programming", LocalDate.of(2021, 3, 25), 5, students);
        CourseView courseView1 = testObject.courseToCourseView(course1);
        List<StudentView> studentViews = testObject.studentsToStudentViews(students);
        Assertions.assertTrue(course1.getId() == courseView1.getId() &&
                course1.getCourseName().equals(courseView1.getCourseName()) &&
                course1.getStartDate().equals(courseView1.getStartDate()) &&
                course1.getWeekDuration() == courseView1.getWeekDuration() &&
                studentViews.equals(courseView1.getStudents()));
        Course course2 = null;
        CourseView courseView2 = testObject.courseToCourseView(course2);
        Assertions.assertNull(courseView2);
    }

    @Test
    //Create two students. Add them to Hashset. Create two courses containing student HashSet.
    //Add courses to a HashSet. Convert to a List of CourseView object. Convert each
    //course to CourseView object. Check if they are in list, and check list size.
    void testCoursesToCourseViews() {
        StudentSequencer.setStudentSequencer(0);
        CourseSequencer.setCourseSequencer(0);
        Collection<Student> students = new HashSet<>();
        Student student1 = new Student(StudentSequencer.nextStudentId(), "Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = new Student(StudentSequencer.nextStudentId(), "Anna Lind", "a.mail@server.com", "myAddress 2");
        students.add(student1);
        students.add(student2);
        Course course1 = new Course(CourseSequencer.nextCourseId(), "Programming", LocalDate.of(2021, 3, 25), 5, students);
        Course course2 = new Course(CourseSequencer.nextCourseId(), "Cooking", LocalDate.of(2022, 2, 6), 10, students);
        Collection<Course> courses1 = new HashSet<>();
        courses1.add(course1);
        courses1.add(course2);
        List<CourseView> courseViews1 = testObject.coursesToCourseViews(courses1);
        CourseView courseView1 = testObject.courseToCourseView(course1);
        CourseView courseView2 = testObject.courseToCourseView(course2);
        Assertions.assertTrue(courseViews1.contains(courseView1));
        Assertions.assertTrue(courseViews1.contains(courseView2));
        Assertions.assertEquals(2, courseViews1.size());
        Collection<Course> courses2 = null;
        List<CourseView> courseViews2 = testObject.coursesToCourseViews(courses2);
        Assertions.assertNull(courseViews2);
    }
}
