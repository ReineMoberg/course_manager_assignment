package se.lexicon.course_manager_assignment.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;

public class StudentTest {

    @Test
    void testSetGetId() {
        StudentSequencer.setStudentSequencer(0);
        Student student1 = new Student();
        Student student2 = new Student();
        Student student3 = new Student(5);
        Assertions.assertEquals(1, student1.getId());
        Assertions.assertEquals(2, student2.getId());
        Assertions.assertEquals(5, student3.getId());
    }

    @Test
    void testSetGetName() {
        Student student1 = new Student();
        Student student2 = new Student("Anna Lind", "e.mail@server.com", "myAddress 1");
        student1.setName("Reine Moberg");
        Assertions.assertEquals("Reine Moberg", student1.getName());
        Assertions.assertEquals("Anna Lind", student2.getName());
    }

    @Test
    void testSetGetEmail() {
        Student student1 = new Student("Anna Lind", "e.mail@server.com", "myAddress 1");
        Student student2 = new Student();
        student2.setEmail("a.mail@server.com");
        Assertions.assertEquals("e.mail@server.com", student1.getEmail());
        Assertions.assertEquals("a.mail@server.com", student2.getEmail());
    }

    @Test
    void testSetGetAddress() {
        Student student1 = new Student("Anna Lind", "e.mail@server.com", "myAddress 1");
        Student student2 = new Student();
        student2.setAddress("myAddress 2");
        Assertions.assertEquals("myAddress 1", student1.getAddress());
        Assertions.assertEquals("myAddress 2", student2.getAddress());
    }
}
