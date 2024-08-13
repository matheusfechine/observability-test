package br.com.performance.observability;

import io.micrometer.observation.ObservationRegistry;
import io.micrometer.observation.aop.ObservedAspect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ObservabilityPerformanceApplication {
	@Bean
	public ObservedAspect observedAspect(ObservationRegistry observationRegistry){
		return new ObservedAspect(observationRegistry);
	}

	public static void main(String[] args) {
		SpringApplication.run(ObservabilityPerformanceApplication.class, args);
	}
}
