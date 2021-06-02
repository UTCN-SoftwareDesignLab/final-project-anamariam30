package com.lab4.demo.course;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.student.model.Student;
import com.lab4.demo.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course,Long> {
    List<Course> findCourseByTeacher(User teacher);
    Course findCourseByTitle(String title);
    List<Course> findCourseByStudentsIsContaining(Student student);
    List<Course> findCourseByDayAndHourAndAndTeacher(String day, String hour,User teacher);
    List<Course> findAllByStudentsContainingAndDayAndHour(Student student,String day, String hour);
}
