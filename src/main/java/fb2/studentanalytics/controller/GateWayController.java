package fb2.studentanalytics.controller;

import fb2.studentanalytics.model.Student;
import fb2.studentanalytics.service.StudentClientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.UUID;

@RestController
@RequestMapping("/api/public")
public class GateWayController {
    private static final Logger log = LoggerFactory.getLogger(GateWayController.class);

    @Autowired
    StudentClientService clientService;

    @GetMapping
    public ResponseEntity<String> checkConnection() {
        return ResponseEntity.ok("Conexión al GateWay funcionando!");
    }

    @GetMapping("/students")
    public ResponseEntity<Flux<Student>> getStudentsViaGateway() {

        // TraceId
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        log.info("Gateway recibe petición para /students");

        Flux<Student> flux = clientService
                .getStudentsFromService()
                .doFinally(signal -> MDC.clear());

        return ResponseEntity
                .ok()
                .header("X-Trace-Id", traceId)
                .body(flux);
    }

    @GetMapping("/students-fail")
    public ResponseEntity<Student> getStudentsViaGWForceFail() {
        // TraceId
        String traceId = UUID.randomUUID().toString();
        MDC.put("traceId", traceId);

        log.info("Gateway recibe petición para /students-fail");

        Student flux = clientService.getStudentsFromServiceFail() ;
        MDC.clear();

        return ResponseEntity
                .ok()
                .header("X-Trace-Id", traceId)
                .body(flux);
    }
}
