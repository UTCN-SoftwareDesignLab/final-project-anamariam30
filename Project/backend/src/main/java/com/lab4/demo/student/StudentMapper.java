package com.lab4.demo.student;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.course.model.dto.CourseDTO;
import com.lab4.demo.student.model.Student;
import com.lab4.demo.student.model.dto.StudentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StudentMapper {

    StudentDTO toDto(Student student);

    Student fromDto(StudentDTO studentDTO);
}
