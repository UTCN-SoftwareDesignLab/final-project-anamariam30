package com.lab4.demo.course;

import com.lab4.demo.course.model.Course;
import com.lab4.demo.course.model.dto.CourseDTO;
import com.lab4.demo.course.model.dto.CourseStudentDTO;
import com.lab4.demo.grade.model.dto.GradeDTO;
import com.lab4.demo.student.StudentRepository;
import com.lab4.demo.student.model.Student;
import com.lab4.demo.user.UserRepository;
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
public class CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final CourseMapper courseMapper;

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Course not found: " + id));
    }

    public List<CourseDTO> findAll() {
        return courseRepository.findAll().stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> findByTeacher(User teacher) {
        return courseRepository.findCourseByTeacher(teacher).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<CourseDTO> findByStudent(Student student) {
        List<Course> c=courseRepository.findCourseByStudentsIsContaining(student);
        return courseRepository.findCourseByStudentsIsContaining(student).stream()
                .map(courseMapper::toDto)
                .collect(Collectors.toList());
    }

    public CourseDTO create(CourseDTO course) {

        Course currCourse=courseMapper.fromDto(course);
        User teacher=userRepository.findById(Long.valueOf(course.getTeacher())).get();
        currCourse.setTeacher(teacher);
        Course save = courseRepository.save(currCourse);
        return courseMapper.toDto(save);
    }

    public CourseDTO edit(CourseDTO course) {
        Course actCourse = findById(course.getId());
        actCourse.setDay(course.getDay());
        actCourse.setHour(course.getHour());
        actCourse.setTeacher(userRepository.findById(Long.valueOf(course.getTeacher())).get());
        return courseMapper.toDto(courseRepository.save(actCourse));
    }

    public void delete(Long id) {
        Optional<Course> course = courseRepository.findById(id);
        courseRepository.delete(course.get());
    }

    public boolean checkIfStudentIsFree(CourseStudentDTO courseDTO)
    {
        Student student=studentRepository.findStudentByName(courseDTO.getStudent());
        Course course=courseRepository.findCourseByTitle(courseDTO.getCourse());
        List<Course> courses=courseRepository.findAllByStudentsContainingAndDayAndHour(student,course.getDay(),course.getHour());
        return courses.isEmpty();
    }

    public CourseDTO addStudentToCourse(CourseStudentDTO courseStudentDTO) {
        Course actCourse = courseRepository.findCourseByTitle(courseStudentDTO.getCourse());
        Student actStudent = studentRepository.findStudentByName(courseStudentDTO.getStudent());
        Set<Student> students = actCourse.getStudents();
        students.add(actStudent);
        actCourse.setStudents(students);
        return courseMapper.toDto(
                courseRepository.save(actCourse)
        );
    }

    public CourseDTO deleteStudentFromCourse(Long courseId, Long studentId) {
        Course actCourse = courseRepository.findById(courseId).get();
        Student actStudent = studentRepository.findById((studentId)).get();
        Set<Student> students = actCourse.getStudents();
        students.remove(actStudent);
        actCourse.setStudents(students);
        return courseMapper.toDto(actCourse);

    }

    public boolean checkIfTeacherIsFree(CourseDTO courseDTO)
    {
        User teacher=userRepository.findById(Long.valueOf(courseDTO.getTeacher())).get();
        List<Course> courses=courseRepository.findCourseByDayAndHourAndAndTeacher(courseDTO.getDay(),courseDTO.getHour(),teacher);
        return courses.isEmpty();

    }



}
