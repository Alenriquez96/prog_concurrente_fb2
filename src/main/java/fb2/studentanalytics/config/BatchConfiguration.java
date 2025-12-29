package fb2.studentanalytics.config;
import fb2.studentanalytics.batch.StudentItemProcessor;
import fb2.studentanalytics.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {
    @Autowired
    private JobBuilderFactory jobBuilderFactory;
    @Autowired
    private StepBuilderFactory stepBuilderFactory;
    @Autowired
    public DataSource dataSource;

    // -------- STEP --------
    @Bean
    public Step importStudentsStep() {
        return stepBuilderFactory.get("importStudentsStep")
                .<Student, Student>chunk(5)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    // -------- JOB --------
    @Bean
    public Job importStudentsJob(Step importStudentsStep) {
        return jobBuilderFactory.get("importStudentsJob")
                .incrementer(new RunIdIncrementer())
                .flow(importStudentsStep)
                .end()
                .build();
    }

    // -------- READER --------
    @Bean
    public FlatFileItemReader<Student> reader(){
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("students.csv"));
        reader.setLinesToSkip(1); // Saltamos la cabecera del archivo CSV
        reader.setStrict(false);
        reader.setLineMapper(new DefaultLineMapper<Student>() {{
            setLineTokenizer(new DelimitedLineTokenizer() {{
                setNames("id","name", "averageGrade");
            }});
            setFieldSetMapper(new BeanWrapperFieldSetMapper<Student>() {{
                setTargetType(Student.class);
            }});
        }});
        return reader;
    }

    // -------- PROCESSOR --------
    @Bean
    public StudentItemProcessor processor() {
        return new StudentItemProcessor();
    }

    // -------- WRITER --------
    @Bean
    public JdbcBatchItemWriter<Student> writer() {
        JdbcBatchItemWriter<Student> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO students (name, averageGrade) VALUES (:name, :averageGrade)");
        writer.setDataSource(dataSource);
        return writer;
    }
}