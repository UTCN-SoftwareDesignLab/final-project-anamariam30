package com.lab4.demo.student.model;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.user.model.User;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@Data
@EqualsAndHashCode(exclude={"courses","course_student"})
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 512, nullable = false)
    private String name;

    @Column(nullable = false)
    private String parentName;

    @Column(length = 512, nullable = false)
    private String address;

    @Column(length = 512, nullable = false)
    private String phoneNo;

    @Column(length = 512, nullable = false)
    private String emailAddress;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "course_student",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id"))

    private Set<Course> courses=new HashSet<>();



}