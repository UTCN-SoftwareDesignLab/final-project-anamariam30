package com.lab4.demo.student;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {
    Student findStudentByName(String name);
    List<Student> findAllByCoursesIsNotContaining(Course course);
    List<Student> findAllByCoursesIsContaining(Course course);


}
