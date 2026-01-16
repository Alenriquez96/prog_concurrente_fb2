package fb2.studentanalytics.service;

import fb2.studentanalytics.model.Student;
import reactor.core.publisher.Flux;

public interface IStudentClientService {
    Flux<Student> getStudentsFromService();

    Student getStudentsFromServiceFail();

    Student fallback(Throwable ex);
}
