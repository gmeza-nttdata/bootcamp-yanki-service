package com.nttdata.bootcamp.yankiservice.application.repository;

import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiRepository {
    Flux<Yanki> get();
    Mono<Yanki> get(Long id);
    Mono<Yanki> create(Yanki yanki);
    Mono<Yanki> update(Long id, Yanki yanki);
    Mono<Void> delete(Long id);
}
