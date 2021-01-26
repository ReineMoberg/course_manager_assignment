package se.lexicon.course_manager_assignment.data.service.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.lexicon.course_manager_assignment.data.dao.CourseDao;
import se.lexicon.course_manager_assignment.data.dao.StudentDao;
import se.lexicon.course_manager_assignment.data.service.converter.Converters;
import se.lexicon.course_manager_assignment.dto.forms.CreateCourseForm;
import se.lexicon.course_manager_assignment.dto.forms.UpdateCourseForm;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Service
public class CourseManager implements CourseService {

    private final CourseDao courseDao;
    private final StudentDao studentDao;
    private final Converters converters;

    @Autowired
    public CourseManager(CourseDao courseDao, StudentDao studentDao, Converters converters) {
        this.courseDao = courseDao;
        this.studentDao = studentDao;
        this.converters = converters;
    }

    @Override
    public CourseView create(CreateCourseForm form) {
        Course course = courseDao.createCourse(form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return converters.courseToCourseView(course);
    }

    //Update course info by id
    @Override
    public CourseView update(UpdateCourseForm form) {
        Course course = courseDao.updateCourse(form.getId(), form.getCourseName(), form.getStartDate(), form.getWeekDuration());
        return converters.courseToCourseView(course);
    }

    @Override
    public List<CourseView> searchByCourseName(String courseName) {
        Collection<Course> courses = courseDao.findByNameContains(courseName);
        return converters.coursesToCourseViews(courses);
    }

    //Find courses that ends before a certain date.
    @Override
    public List<CourseView> searchByDateBefore(LocalDate end) {
        Collection<Course> courses = courseDao.findByDateBefore(end);
        return converters.coursesToCourseViews(courses);
    }

    //Find courses that starts after a certain date.
    @Override
    public List<CourseView> searchByDateAfter(LocalDate start) {
        Collection<Course> courses = courseDao.findByDateAfter(start);
        return converters.coursesToCourseViews(courses);
    }

    @Override
    public boolean addStudentToCourse(int courseId, int studentId) {
        boolean success = false;
        Student student = studentDao.findById(studentId);
        Course course = courseDao.findById(courseId);
        boolean successAdded = course.enrollStudent(student);
        if (successAdded) {
            Course updatedCourse = courseDao.updateCourse(course.getId(), course.getCourseName(), course.getStartDate(), course.getWeekDuration(), course.getStudents());
            if (updatedCourse.equals(course)) {
                success = true;
            }
        }
        return success;
    }

    @Override
    public boolean removeStudentFromCourse(int courseId, int studentId) {
        boolean success = false;
        Student student = studentDao.findById(studentId);
        Course course = courseDao.findById(courseId);
        boolean successRemoved = course.unenrollStudent(student);
        if (successRemoved) {
            Course updatedCourse = courseDao.updateCourse(course.getId(), course.getCourseName(), course.getStartDate(), course.getWeekDuration(), course.getStudents());
            if (updatedCourse.equals(course)) {
                success = true;
            }
        }
        return success;
    }

    @Override
    public CourseView findById(int id) {
        Course course = courseDao.findById(id);
        return converters.courseToCourseView(course);
    }

    @Override
    public List<CourseView> findAll() {
        Collection<Course> courses = courseDao.findAll();
        return converters.coursesToCourseViews(courses);
    }

    @Override
    public List<CourseView> findByStudentId(int studentId) {
        Collection<Course> courses = courseDao.findByStudentId(studentId);
        return converters.coursesToCourseViews(courses);
    }

    @Override
    public boolean deleteCourse(int id) {
        Course course = courseDao.findById(id);
        return courseDao.removeCourse(course);
    }
}
