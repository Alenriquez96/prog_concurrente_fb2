package fb2.studentanalytics.config;

import fb2.studentanalytics.batch.StudentItemProcessor;
import fb2.studentanalytics.model.Student;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
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
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {
    @Autowired
    public DataSource dataSource;

    // -------- STEP --------
    @Bean
    public Step importStudentsStep(JobRepository jobRepository, PlatformTransactionManager transactionManager) {
        return new StepBuilder("importStudentsStep", jobRepository)
                .<Student, Student>chunk(5, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    // -------- JOB --------
    @Bean
    public Job importStudentsJob(JobRepository jobRepository, Step importStudentsStep) {
        return new JobBuilder("importStudentsJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(importStudentsStep)
                .build();
    }

    // -------- READER --------
    @Bean
    public FlatFileItemReader<Student> reader() {
        FlatFileItemReader<Student> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("students.csv"));
        reader.setLinesToSkip(1);
        reader.setStrict(true);

        DefaultLineMapper<Student> lineMapper = new DefaultLineMapper<>();

        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setNames("id", "name", "averageGrade");

        BeanWrapperFieldSetMapper<Student> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(Student.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);
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
        writer.setItemSqlParameterSourceProvider(
                new BeanPropertyItemSqlParameterSourceProvider<>()
        );
        writer.setSql("INSERT INTO STUDENT (name, average_grade) VALUES (:name, :averageGrade)");
        writer.setDataSource(dataSource);
        return writer;
    }
}
