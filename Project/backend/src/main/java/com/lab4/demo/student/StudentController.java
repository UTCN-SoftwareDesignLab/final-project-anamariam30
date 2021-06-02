package com.lab4.demo.student;

import com.lab4.demo.course.CourseService;
import com.lab4.demo.course.model.dto.CourseDTO;
import com.lab4.demo.course.model.dto.CourseStudentDTO;
import com.lab4.demo.email.EmailService;
import com.lab4.demo.student.model.dto.StudentDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.lab4.demo.UrlMapping.*;

@RestController
@RequestMapping(STUDENTS)
@RequiredArgsConstructor
public class StudentController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EmailService emailService;

    @GetMapping
    public List<StudentDTO> allStudents() {
        List<StudentDTO> all = studentService.findAll();
        return all;
    }

    @PostMapping
    public StudentDTO create(@RequestBody StudentDTO student) {
        return studentService.create(student);
    }

    @PatchMapping
    public StudentDTO edit(@RequestBody StudentDTO course) {
        return studentService.edit(course);
    }


    @GetMapping("/enrolled/{id}")
    public List<String> allStudentsEnrolled(@PathVariable @NonNull Long id) {
        List<String> all = studentService.findAllByCourse(courseService.findById(id));
        return all;
    }

    @GetMapping("/notEnrolled/{id}")
    public List<String> allStudentsNotEnrolled(@PathVariable @NonNull Long id) {
        List<String> all = studentService.findAllNotAtCourse(courseService.findById(id));
        return all;
    }

    @PostMapping("/email")
    public void sendEmail(@RequestBody StudentDTO student) {
         emailService.sendMailWithAttachment(student);
    }

}
