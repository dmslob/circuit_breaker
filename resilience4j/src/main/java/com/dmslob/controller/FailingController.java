package com.dmslob.controller;

import com.dmslob.service.FailingService;
import lombok.extern.log4j.Log4j2;
import org.reactivestreams.Publisher;
import org.springframework.cloud.circuitbreaker.commons.ReactiveCircuitBreaker;
import org.springframework.cloud.circuitbreaker.commons.ReactiveCircuitBreakerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Log4j2
@RestController
public class FailingController {

    private final FailingService failingService;
    private final ReactiveCircuitBreaker circuitBreaker;

    public FailingController(FailingService failingService, ReactiveCircuitBreakerFactory cbf) {
        this.failingService = failingService;
        this.circuitBreaker = cbf.create("greet");
    }

    @GetMapping("/greet")
    public Publisher<String> greet(@RequestParam Optional<String> name) {
        Mono<String> results = this.failingService.greet(name);
        // run with fallback
        return this.circuitBreaker.run(results, throwable -> fallback());
    }

    private Mono<String> fallback() {
        return Mono.just("Hello World|");
    }
}
