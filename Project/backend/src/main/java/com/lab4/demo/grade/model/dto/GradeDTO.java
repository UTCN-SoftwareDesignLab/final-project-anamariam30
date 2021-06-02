package com.lab4.demo.grade.model.dto;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.student.model.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GradeDTO {
    private Long id;
    private String course;
    private String student;
    private Integer grade;
    private Date data;


}
