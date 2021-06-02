package com.lab4.demo.course;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.course.model.dto.CourseDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CourseMapper {
    @Mapping(source="teacher.username",target = "teacher")
    CourseDTO toDto(Course course);
    @Mapping(target="teacher.username",source = "teacher")
    Course fromDto(CourseDTO courseDTO);
}
