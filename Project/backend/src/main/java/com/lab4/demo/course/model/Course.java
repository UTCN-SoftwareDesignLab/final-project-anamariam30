package com.lab4.demo.course.model;

import com.lab4.demo.student.model.Student;
import com.lab4.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(exclude={"students","course_student"})
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String title;

    @Column(length = 512, nullable = false)
    private String day;

    @Column
    private String hour;

    @ManyToOne
    @JoinColumn(name="teacher_id")
    private User teacher;


    @ManyToMany(mappedBy = "courses")
    private Set<Student> students=new HashSet<>();

}