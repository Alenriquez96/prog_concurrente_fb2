package fb2.studentanalytics.repository;

import fb2.studentanalytics.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, Long> {
    public Flux<Student> getStudentsByAverageGradeGreaterThanEqual(Integer min);
}
