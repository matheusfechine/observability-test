package br.com.performance.observability.api;

import br.com.performance.observability.model.User;
import br.com.performance.observability.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class RestApiController {

    private static final String STATUS_COUNT = "status.count";

    private final UserRepository repository;
    private final MeterRegistry meterRegistry;

    @GetMapping(value = "/list/users", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<User>> listUsers() {
        Counter status;
        try {
            status = Counter.builder("status.count")
                    .tag("method", "listUsers")
                    .tag("status", "200")
                    .register(meterRegistry);
            status.increment();
            return ResponseEntity.ok(repository.findAll());
        } catch (RuntimeException ex) {
            status = Counter.builder("status.count")
                    .tag("method", "listUsers")
                    .tag("status", "500")
                    .register(meterRegistry);
            status.increment();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(value = "/create/user", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<User> createUsers() {
        Counter status;
        try {
            status = Counter.builder(STATUS_COUNT)
                    .tag("status", "200")
                    .tag("method", "createUsers")
                    .register(meterRegistry);
            status.increment();
            return ResponseEntity.ok(repository.save(User.builder().name(UUID.randomUUID().toString()).build()));
        } catch (RuntimeException ex) {
            status = Counter.builder("status.count")
                    .tag("status", "500")
                    .tag("method", "createUsers")
                    .register(meterRegistry);
            status.increment();
            return ResponseEntity.badRequest().build();
        }
    }
}
