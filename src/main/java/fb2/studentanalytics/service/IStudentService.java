package fb2.studentanalytics.service;

import fb2.studentanalytics.model.Student;
import reactor.core.publisher.Flux;

import java.time.Duration;

public interface IStudentService {
    public Flux<Student> getAllStudentsAsStream();

    public Flux<Student> getAllStudents(Double min);

    public Flux<Student> getAllStudents();
}
