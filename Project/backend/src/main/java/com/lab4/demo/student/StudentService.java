package com.lab4.demo.student;

import com.lab4.demo.course.CourseMapper;
import com.lab4.demo.course.CourseRepository;
import com.lab4.demo.course.CourseService;
import com.lab4.demo.course.model.Course;
import com.lab4.demo.course.model.dto.CourseDTO;
import com.lab4.demo.course.model.dto.CourseStudentDTO;
import com.lab4.demo.grade.GradeRepository;
import com.lab4.demo.grade.GradeService;
import com.lab4.demo.student.model.Student;
import com.lab4.demo.student.model.dto.StudentDTO;
import com.lab4.demo.user.model.ERole;
import com.lab4.demo.user.model.Role;
import com.lab4.demo.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final StudentMapper studentMapper;
    private final GradeRepository gradeRepository;
    private final GradeService gradeService;
    public Student findById(Long id) {
        Optional<Student> byId = studentRepository.findById(id);
        return byId
                .orElseThrow(() -> new EntityNotFoundException("Student not found: " + id));
    }

    public List<StudentDTO> findAll() {
        return studentRepository.findAll().stream()
                .map(studentMapper::toDto)
                .collect(Collectors.toList());
    }

    public StudentDTO create(StudentDTO student) {
        Student currCourse=studentMapper.fromDto(student);
        return studentMapper.toDto(studentRepository.save(currCourse));
    }


    public StudentDTO edit(StudentDTO student) {

        Student actStudent = findById(student.getId());
        actStudent.setAddress(student.getAddress());
        actStudent.setEmailAddress(student.getEmailAddress());
        actStudent.setPhoneNo(student.getPhoneNo());

        return studentMapper.toDto(
                studentRepository.save(actStudent)
        );
    }

    public List<String> findAllByCourse(Course course) {
        return studentRepository.findAllByCoursesIsContaining(course).stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public List<String> findAllNotAtCourse(Course course) {
        return studentRepository.findAllByCoursesIsNotContaining(course).stream()
                .map(Student::getName)
                .collect(Collectors.toList());
    }

    public void addStudentToCourse(CourseStudentDTO courseStudentDTO) {
        Course actCourse = courseRepository.findCourseByTitle(courseStudentDTO.getCourse());
        Student actStudent = studentRepository.findStudentByName(courseStudentDTO.getStudent());
        actStudent.getCourses().add(actCourse);
        studentRepository.save(actStudent);
    }

    public void removeStudentFromCourse(CourseStudentDTO courseStudentDTO) {
        Course actCourse = courseRepository.findCourseByTitle(courseStudentDTO.getCourse());
        Student actStudent = studentRepository.findStudentByName(courseStudentDTO.getStudent());
        actStudent.getCourses().remove(actCourse);
        studentRepository.save(actStudent);
    }

}
