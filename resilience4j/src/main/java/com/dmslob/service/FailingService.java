package com.dmslob.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Optional;

@Log4j2
@Service
public class FailingService {

    public Mono<String> greet(Optional<String> name) {
        long seconds = (long) (Math.random() * 10);
        log.info(seconds + "sec");

        return name
                .map(str -> {
                    String message = "Hello " + str + "! (in " + seconds + ")";
                    log.info(message);
                    return Mono.just(message);
                })
                .orElse(Mono.error(new NullPointerException()))
                .delayElement(Duration.ofSeconds(seconds));
    }
}
