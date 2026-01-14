package fb2.studentanalytics.repository;

import fb2.studentanalytics.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    public List<Student> getStudentsByAverageGradeGreaterThanEqual(Integer min);
}
