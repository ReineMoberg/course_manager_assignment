package se.lexicon.course_manager_assignment.data.service.converter;

import org.springframework.stereotype.Component;
import se.lexicon.course_manager_assignment.dto.views.CourseView;
import se.lexicon.course_manager_assignment.dto.views.StudentView;
import se.lexicon.course_manager_assignment.model.Course;
import se.lexicon.course_manager_assignment.model.Student;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class ModelToDto implements Converters {

    @Override
    public StudentView studentToStudentView(Student student) {
        StudentView result = null;
        if (student != null) {
            result = new StudentView(student.getId(), student.getName(), student.getEmail(), student.getAddress());
        }
        return result;
    }

    @Override
    public CourseView courseToCourseView(Course course) {
        CourseView result = null;
        if (course != null) {
            result = new CourseView(course.getId(), course.getCourseName(), course.getStartDate(), course.getWeekDuration(), studentsToStudentViews(course.getStudents()));
        }
        return result;
    }

    @Override
    public List<CourseView> coursesToCourseViews(Collection<Course> courses) {
        List<CourseView> result = null;
        if (courses != null) {
            result = new ArrayList<>();
            for (Course course : courses) {
                result.add(courseToCourseView(course));
            }
        }
        return result;
    }

    @Override
    public List<StudentView> studentsToStudentViews(Collection<Student> students) {
        List<StudentView> result = null;
        if (students != null) {
            result = new ArrayList<>();
            for (Student student : students) {
                result.add(studentToStudentView(student));
            }
        }
        return result;
    }
}
