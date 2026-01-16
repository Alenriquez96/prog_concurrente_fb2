package fb2.studentanalytics.service;

import fb2.studentanalytics.model.Student;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Service
public class StudentClientService {
    @Autowired
    WebClient webClient;

    @CircuitBreaker(name = "studentsCB", fallbackMethod = "fallback")
    public Flux<Student> getStudentsFromService() {
        return webClient.get()
                .uri("/students")
                .retrieve()
                .bodyToFlux(Student.class)
                .delayElements(Duration.ofMillis(50)); // simulamos lentitud
    }

    @CircuitBreaker(name = "studentsCB", fallbackMethod = "fallback")
    public Student getStudentsFromServiceFail() {
        // Forzamos un fallo inmediato
        throw new RuntimeException("Simulando fallo");
    }

    // Fallback
    public Student fallback(Throwable ex) {
        return new Student("Fallback Student", 0);
    }
}