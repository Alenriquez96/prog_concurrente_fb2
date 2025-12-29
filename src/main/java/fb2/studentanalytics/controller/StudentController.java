package fb2.studentanalytics.controller;

import fb2.studentanalytics.model.Student;
import fb2.studentanalytics.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    StudentService studentService;

    @GetMapping
    public Flux<Student> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/top")
    public Flux<Student> getTopStudents(@RequestParam("min") int min) {
        return studentService.getAllStudents(min);
    }

    @GetMapping("/stream")
    public Stream<Student> getAllStudentsAsStream() {
        return studentService.getAllStudentsAsStream();
    }
}
