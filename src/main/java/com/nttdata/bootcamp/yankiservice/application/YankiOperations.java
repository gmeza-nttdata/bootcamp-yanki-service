package com.nttdata.bootcamp.yankiservice.application;

import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiOperations {
    Flux<Yanki> getAll();
    Mono<Yanki> get(Long id);
    Mono<Yanki> create(Yanki yanki);
    Mono<Yanki> associateDebitCard(Long id, String cardId);
    Mono<Void> delete(Long id);
}
