package com.lab4.demo.grade;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.grade.model.Grade;
import com.lab4.demo.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GradeRepository extends JpaRepository<Grade,Long> {

    List<Grade> findAllByStudent(Student student);
    List<Grade> findAllByStudentAndCourse(Student student, Course course);
    List<Grade> findAllByCourse(Course course);
    void deleteAllByStudent(Student student);
}
