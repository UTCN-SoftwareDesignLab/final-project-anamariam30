package com.lab4.demo.grade;

import com.lab4.demo.course.CourseRepository;
import com.lab4.demo.course.model.Course;
import com.lab4.demo.grade.model.Grade;
import com.lab4.demo.grade.model.dto.GradeDTO;
import com.lab4.demo.student.StudentRepository;
import com.lab4.demo.student.model.Student;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {
    private final GradeRepository gradeRepository;
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final GradeMapper gradeMapper;

    public GradeDTO create(GradeDTO grade) {
        Grade currGrade=gradeMapper.fromDto(grade);
        Course course=courseRepository.findCourseByTitle(grade.getCourse());
        currGrade.setCourse(course);
        Student student=studentRepository.findStudentByName(grade.getStudent());
        currGrade.setStudent(student);
        Grade save = gradeRepository.save(currGrade);
        return gradeMapper.toDto(save);
    }

    public List<GradeDTO> getStudentsGrade(String name){
        Student student=studentRepository.findStudentByName(name);

        return  gradeRepository.findAllByStudent(student).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GradeDTO> getStudentsGrade(Long id){
        Student student=studentRepository.findById(id).get();
        return  gradeRepository.findAllByStudent(student).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<GradeDTO> getStudentCourseGrade(String student_id,String course_id){
        Student student=studentRepository.findStudentByName(student_id);
        Course course=courseRepository.findCourseByTitle(course_id);
        return  gradeRepository.findAllByStudentAndCourse(student,course).stream()
                .map(gradeMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id)
    {
        Optional<Grade> grade = gradeRepository.findById(id);
        gradeRepository.delete(grade.get());
    }

    public void deleteAlLByStudent(Student student)
    {
        gradeRepository.deleteAllByStudent(student);
    }

    public GradeDTO edit(GradeDTO grade) {
        Grade currGrade=gradeRepository.findById(grade.getId()).get();
        currGrade.setGrade(grade.getGrade());
        Grade save = gradeRepository.save(currGrade);
        return gradeMapper.toDto(save);
    }

}
