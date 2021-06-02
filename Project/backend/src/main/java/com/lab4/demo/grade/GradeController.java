package com.lab4.demo.grade;

import com.lab4.demo.course.CourseService;
import com.lab4.demo.course.model.dto.CourseStudentDTO;
import com.lab4.demo.email.EmailService;
import com.lab4.demo.grade.model.dto.GradeDTO;
import com.lab4.demo.report.ReportServiceFactory;
import com.lab4.demo.student.StudentService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.util.List;

import static com.lab4.demo.UrlMapping.*;
import static com.lab4.demo.report.ReportType.PDF;

@RestController
@RequestMapping(GRADES)
@RequiredArgsConstructor
public class GradeController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final GradeService gradeService;
    private final ReportServiceFactory reportServiceFactory;


    @PostMapping("/names")
    public List<GradeDTO> allGradesForStudent(@RequestBody CourseStudentDTO name) {
        List<GradeDTO> all = gradeService.getStudentCourseGrade(name.getStudent(),name.getCourse());

        return all;
    }

    @PostMapping
    public GradeDTO create(@RequestBody GradeDTO grade) {

        return gradeService.create(grade);
    }


    @DeleteMapping(ENTITY)
    public void delete(@PathVariable @NonNull Long id) {
        gradeService.delete(id);

    }


    @GetMapping(EXPORT_REPORT+ENTITY)
    public void exportReport(@PathVariable @NonNull Long id) throws InterruptedException {
        reportServiceFactory.getReportService(PDF).export(gradeService.getStudentsGrade(id),id);
        Thread.sleep(2000);
    }
    @RequestMapping(value = "/file/{id:.+}", method = RequestMethod.GET, produces = "application/pdf")
    public ResponseEntity<InputStreamResource> download(@PathVariable("id") Long id) throws IOException, InterruptedException {
        Thread.sleep(1000);
        ClassPathResource pdfFile = new ClassPathResource("ClassReport" +id+ ".pdf");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/pdf"));
        headers.add("Content-Disposition", "filename=" + "ClassReport.pdf");
        long contentLength = pdfFile.contentLength();
        headers.setContentLength(contentLength);
        ResponseEntity<InputStreamResource> response = new ResponseEntity<InputStreamResource>(
                new InputStreamResource(pdfFile.getInputStream()), headers, HttpStatus.OK);
        return response;

    }


}
