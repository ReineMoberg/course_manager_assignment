package se.lexicon.course_manager_assignment.data.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.Collection;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertFalse;

@SpringBootTest(classes = {StudentCollectionRepository.class})
public class StudentCollectionRepositoryTest {

    @Autowired
    private StudentDao testObject;

    @Test
    @DisplayName("Test context successfully setup")
    void context_loads() {
        assertFalse(testObject == null);
    }

    //Write your tests here

    @Test
    //Create two students and add to collection in StudentCollectionRepository.
    //New id is generated automatically. Check if students are added successfully
    //by checking if they exist in collection. Print for visual confirmation.
    void testCreateStudentAndFindAll() {
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Assertions.assertTrue(testObject.findAll().contains(student1));
        Assertions.assertTrue(testObject.findAll().contains(student2));
        System.out.println(student1);
        System.out.println(student2);
        System.out.println(testObject.findAll());
    }

    @Test
    //Check that collection is empty. Add two students. Check that collection consists
    //of two students.
    void testFindAll() {
        Assertions.assertEquals(0, testObject.findAll().size());
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Assertions.assertEquals(2, testObject.findAll().size());
    }

    @Test
    //Add three students to collection. Search by email on two of them, case insensitive.
    //Search for email that doesn't exist and check that null, ie empty, is returned.
    void testFindByEmailIgnoreCase() {
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Student student3 = testObject.createStudent("Kalle Carlsson", "B.Mail@server.com", "myAddress3");
        Assertions.assertEquals(student3, testObject.findByEmailIgnoreCase("b.mail@server.com"));
        Assertions.assertEquals(student2, testObject.findByEmailIgnoreCase("A.MAIL@server.com"));
        Assertions.assertNull(testObject.findByEmailIgnoreCase("c.mail@server.com"));
    }

    @Test
    //Add two students to collection. Search by id and check that correct student is returned.
    //Search for an id that doesn't exist and check that null, ie empty, is returned.
    void testFindById() {
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Assertions.assertEquals(student1, testObject.findById(1));
        Assertions.assertEquals(student2, testObject.findById(2));
        Assertions.assertNull(testObject.findById(3));
    }

    @Test
    //Add two students to collection. Check that they were added. Remove one student
    //and check if removed. Try to remove the same student again, ie not in collection.
    //Print collection for visual confirmation.
    void testRemoveStudent() {
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Assertions.assertEquals(student1, testObject.findById(1));
        Assertions.assertEquals(student2, testObject.findById(2));
        Assertions.assertTrue(testObject.removeStudent(student1));
        Assertions.assertNull(testObject.findById(1));
        Assertions.assertFalse(testObject.removeStudent(student1));
        System.out.println(testObject.findAll());
    }

    @Test
    //Add three students to collection. Two of them with the same first name.
    //Find a collection of students by searching part of name, case insensitive.
    //Search a student not in collection.
    void testFindByNameContains() {
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Student student3 = testObject.createStudent("Reine Anderson", "b.mail@server.com", "myAddress 3");
        Collection<Student> result1 = testObject.findByNameContains("anna");
        Collection<Student> result2 = testObject.findByNameContains("Reine");
        Collection<Student> result3 = testObject.findByNameContains("moberg");
        Collection<Student> result4 = testObject.findByNameContains("Ola");
        Collection<Student> compare1 = new HashSet<>();
        compare1.add(student2);
        Collection<Student> compare2 = new HashSet<>();
        compare2.add(student1);
        compare2.add(student3);
        Collection<Student> compare3 = new HashSet<>();
        compare3.add(student1);
        Collection<Student> compare4 = new HashSet<>();
        Assertions.assertEquals(compare1, result1);
        Assertions.assertEquals(compare2, result2);
        Assertions.assertEquals(compare3, result3);
        Assertions.assertEquals(compare4, result4);
    }

    @Test
    //Check that collection is empty. Add two students. Check that collection consists
    //of two students. Clear collection and check if empty.
    void testClear() {
        Assertions.assertEquals(0, testObject.findAll().size());
        Student student1 = testObject.createStudent("Reine Moberg", "e.mail@server.com", "myAddress 1");
        Student student2 = testObject.createStudent("Anna Lind", "a.mail@server.com", "myAddress 2");
        Assertions.assertEquals(2, testObject.findAll().size());
        testObject.clear();
        Assertions.assertEquals(0, testObject.findAll().size());
    }

    @AfterEach
    void tearDown() {
        testObject.clear();
        StudentSequencer.setStudentSequencer(0);
    }
}
