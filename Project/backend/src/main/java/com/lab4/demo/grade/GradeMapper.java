package com.lab4.demo.grade;

import com.lab4.demo.grade.model.Grade;
import com.lab4.demo.grade.model.dto.GradeDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    @Mapping(source="course.title",target = "course")
    @Mapping(source="student.name",target = "student")
    GradeDTO toDto(Grade grade);
    @Mapping(target="course.title",source = "course")
    @Mapping(target="student.name",source = "student")
    @Mapping(target="data",dateFormat = "yyyy-MM-dd HH:mm")
    Grade fromDto(GradeDTO gradeDTO);
}
