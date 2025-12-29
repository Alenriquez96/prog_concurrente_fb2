package fb2.studentanalytics.batch;

import fb2.studentanalytics.model.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class StudentItemProcessor implements ItemProcessor<Student, Student> {
    private static final Logger log = LoggerFactory.getLogger(StudentItemProcessor.class);

    @Override
    public Student process(Student student) throws Exception {
        final String name = student.getName().toUpperCase();
        final int averageGrade = student.getAverageGrade();
        final Student transformedStudent = new Student(name, averageGrade);
        log.info("Converting (" + student + ") into (" + transformedStudent + ")");
        return transformedStudent;
    }
}
