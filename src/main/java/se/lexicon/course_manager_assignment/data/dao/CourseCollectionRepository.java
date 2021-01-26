package se.lexicon.course_manager_assignment.data.dao;



import se.lexicon.course_manager_assignment.data.sequencers.CourseSequencer;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;


public class CourseCollectionRepository implements CourseDao{

    private Collection<Course> courses;


    public CourseCollectionRepository(Collection<Course> courses) {
        this.courses = courses;
    }

    @Override
    public Course createCourse(String courseName, LocalDate startDate, int weekDuration) {
        Course course = new Course(CourseSequencer.nextCourseId());
        course.setCourseName(courseName);
        course.setStartDate(startDate);
        course.setWeekDuration(weekDuration);
        courses.add(course);
        return course;
    }

    @Override
    public Course findById(int id) {
        Course result = null;
        for (Course course : courses) {
            if (course.getId() == id) {
                result = course;
                break;
            }
        }
        return result;
    }

    @Override
    public Collection<Course> findByNameContains(String name) {
        Collection<Course> result = new HashSet<>();
        for (Course course : courses){
            if (course.getCourseName().toLowerCase().contains(name.toLowerCase())){
                result.add(course);
            }
        }
        return result;
    }

    //Find courses that ends before a certain date.
    @Override
    public Collection<Course> findByDateBefore(LocalDate end) {
        Collection<Course> result = new HashSet<>();
        for (Course course : courses) {
            if (course.getStartDate().plusWeeks(course.getWeekDuration()).isBefore(end)) {
                result.add(course);
            }
        }
        return result;
    }

    //Find courses that starts after a certain date.
    @Override
    public Collection<Course> findByDateAfter(LocalDate start) {
        Collection<Course> result = new HashSet<>();
        for (Course course : courses) {
            if (course.getStartDate().isAfter(start)) {
                result.add(course);
            }
        }
        return result;
    }

    @Override
    public Collection<Course> findAll() {
        return courses;
    }

    @Override
    public Collection<Course> findByStudentId(int studentId) {
        Collection<Course> result = new HashSet<>();
        Collection<Student> students;
        for (Course course : courses) {
            students = course.getStudents();
            for (Student student : students){
                if (student.getId() == studentId) {
                    result.add(course);
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public boolean removeCourse(Course course) {
        return courses.remove(course);
    }

    @Override
    public void clear() {
        this.courses = new HashSet<>();
    }

    @Override
    public Course updateCourse(int id, String courseName, LocalDate startDate, int weekDuration) {
        Collection<Student> students = findById(id).getStudents();
        boolean removeSuccess = removeCourse(findById(id));
        if (removeSuccess) {
            Course course = new Course(id);
            course.setCourseName(courseName);
            course.setStartDate(startDate);
            course.setWeekDuration(weekDuration);
            course.setStudents(students);
            courses.add(course);
            return course;
        } else {
            return findById(id);
        }
    }

    @Override
    public Course updateCourse(int id, String courseName, LocalDate startDate, int weekDuration, Collection<Student> students) {
        boolean removeSuccess = removeCourse(findById(id));
        if (removeSuccess) {
            Course course = new Course(id);
            course.setCourseName(courseName);
            course.setStartDate(startDate);
            course.setWeekDuration(weekDuration);
            course.setStudents(students);
            courses.add(course);
            return course;
        } else {
            return findById(id);
        }
    }
}
