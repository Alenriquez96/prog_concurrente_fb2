package fb2.studentanalytics.controller;

import fb2.studentanalytics.model.Student;
import fb2.studentanalytics.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.stream.Stream;

@RestController
@RequestMapping("/students")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class);

    @Autowired
    StudentService studentService;

    @GetMapping()
    public Flux<Student> getAllStudents() {
        log.info("Recuperando todos los estudiantes...");
        return studentService.getAllStudents();
    }

    @GetMapping(value="/top")
    public Flux<Student> getTopStudents(@RequestParam("min") String min) {
        log.info("Recuperando todos los estudiantes con una nota mínima de " + min + "...");
        return studentService.getAllStudents(Double.parseDouble(min));
    }

    @GetMapping(value = "/stream", produces = MediaType.TEXT_EVENT_STREAM_VALUE) // El segundo parametro es clave para que funcione el FLUX y lo veamos en tiemp real
    public Flux<Student> getAllStudentsAsStream() {
        log.info("Recuperando todos los estudiantes como stream...");
        return studentService.getAllStudentsAsStream();
    }
}
