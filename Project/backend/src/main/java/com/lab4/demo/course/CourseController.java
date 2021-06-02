package com.lab4.demo.course;

import com.lab4.demo.course.model.dto.CourseDTO;
import com.lab4.demo.course.model.dto.CourseStudentDTO;
import com.lab4.demo.security.dto.MessageResponse;
import com.lab4.demo.student.StudentService;
import com.lab4.demo.student.model.dto.StudentDTO;
import com.lab4.demo.user.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.COURSES;
import static com.lab4.demo.UrlMapping.ENTITY;

@RestController
@RequestMapping(COURSES)
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final StudentService studentService;
    private final UserService userService;

    @GetMapping
    public List<CourseDTO> allCourses() {
        List<CourseDTO> all = courseService.findAll();
        return all;
    }
    @PostMapping
    public ResponseEntity<?> create(@RequestBody CourseDTO course) {
        if (courseService.checkIfTeacherIsFree(course)) {
            CourseDTO courseDTO = courseService.create(course);
            return new ResponseEntity<>(courseDTO, HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>( HttpStatus.CONFLICT );

        }
    }

    @PatchMapping
    public  ResponseEntity<?> edit(@RequestBody CourseDTO course) {
        if (courseService.checkIfTeacherIsFree(course)) {
            CourseDTO courseDTO = courseService.edit(course);
            return new ResponseEntity<>(courseDTO, HttpStatus.ACCEPTED);
        } else {
             return new ResponseEntity<>( HttpStatus.CONFLICT );
        }
    }

    @DeleteMapping(ENTITY)
    public ResponseEntity<?> delete(@PathVariable @NonNull Long id) {
        try{
        courseService.delete(id);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            return  new ResponseEntity<>( HttpStatus.CONFLICT );
        }
    }

    @PostMapping("/adds")
    public ResponseEntity<?> addStudentToCourse(@RequestBody CourseStudentDTO student) {
        if (courseService.checkIfStudentIsFree(student)) {
            studentService.addStudentToCourse(student);
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } else {
            return new ResponseEntity<>( HttpStatus.CONFLICT);
        }

    }

    @PostMapping("/remove")
    public void removeStudentToCourse(@RequestBody CourseStudentDTO student) {
        studentService.removeStudentFromCourse(student);
    }

    @GetMapping("/fetch/{id}")
    public List<CourseDTO> allTeacherCourses(@PathVariable @NonNull Long id) {
        return courseService.findByTeacher(userService.findById(id));
    }

    @GetMapping("/fetchCourses/{id}")
    public List<CourseDTO> allStudentCourses(@PathVariable @NonNull Long id) {
        List<CourseDTO> student = courseService.findByStudent(studentService.findById(id));
        return student;
    }



}
