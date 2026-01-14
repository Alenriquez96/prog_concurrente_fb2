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

    public Stream<Student> getAllStudentsAsStream() {
        return Flux.fromIterable(studentRepo.findAll()).delayElements(Duration.ofSeconds(1)).toStream();
    }

    public Flux<Student> getAllStudents(Double min) {
        Flux<Student> studentFlux = Flux.fromIterable(studentRepo.getStudentsByAverageGradeGreaterThanEqual(min)).delayElements(Duration.ofSeconds(1));
        return studentFlux;
    }

    public Flux<Student> getAllStudents() {
        Flux<Student> studentFlux = Flux.fromIterable(studentRepo.findAll()).delayElements(Duration.ofSeconds(1));
        return studentFlux;
    }
}
