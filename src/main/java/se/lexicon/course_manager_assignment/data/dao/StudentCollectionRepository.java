package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.StudentSequencer;
import se.lexicon.course_manager_assignment.model.Student;

import java.util.Collection;
import java.util.HashSet;


public class StudentCollectionRepository implements StudentDao {

    private Collection<Student> students;

    public StudentCollectionRepository(Collection<Student> students) {
        this.students = students;
    }

    @Override
    public Student createStudent(String name, String email, String address) {
        Student student = new Student(StudentSequencer.nextStudentId());
        student.setName(name);
        student.setEmail(email);
        student.setAddress(address);
        students.add(student);
        return student;
    }

    @Override
    public Student findByEmailIgnoreCase(String email) {
        Student result = null;
        for (Student student : students){
            if (student.getEmail().equalsIgnoreCase(email)) {
                result = student;
                break;
            }
        }
        return result;
    }

    @Override
    public Collection<Student> findByNameContains(String name) {
        Collection<Student> result = new HashSet<>();
        for (Student student : students) {
            if (student.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(student);
            }
        }
        return result;
    }

    @Override
    public Student findById(int id) {
        Student result = null;
        for (Student student : students){
            if (student.getId() == id) {
                result = student;
                break;
            }
        }
        return result;
    }

    @Override
    public Collection<Student> findAll() {
        return students;
    }

    @Override
    public boolean removeStudent(Student student) {
        return students.remove(student);
    }

    @Override
    public void clear() {
        this.students = new HashSet<>();
    }

    @Override
    public Student updateStudent(int id, String name, String email, String address) {
        boolean removeSuccess = removeStudent(findById(id));
        if (removeSuccess) {
            Student student = new Student(id);
            student.setName(name);
            student.setEmail(email);
            student.setAddress(address);
            students.add(student);
            return student;
        } else {
            return findById(id);
        }
    }
}
