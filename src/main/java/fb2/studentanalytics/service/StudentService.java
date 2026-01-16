package fb2.studentanalytics.service;

import fb2.studentanalytics.model.Student;
import fb2.studentanalytics.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.stream.Stream;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepo;

    public Flux<Student> getAllStudentsAsStream() {
        return Flux.fromIterable(studentRepo.findAll()).delayElements(Duration.ofMillis(50));
    }

    public Flux<Student> getAllStudents(Double min) {
        Flux<Student> studentFlux = Flux.fromIterable(studentRepo.findAll()).filter(student -> student.getAverageGrade() >= min);
        return studentFlux;
    }

    public Flux<Student> getAllStudents() {
        Flux<Student> studentsFlux = Flux.fromIterable(studentRepo.findAll());
        return studentsFlux;
    }
}
