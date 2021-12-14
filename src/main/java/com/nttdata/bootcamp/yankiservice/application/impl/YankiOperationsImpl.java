package com.nttdata.bootcamp.yankiservice.application.impl;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.application.repository.YankiRepository;
import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor
public class YankiOperationsImpl implements YankiOperations {

    private final YankiRepository repository;


    @Override
    public Flux<Yanki> getAll() {
        return repository.get();
    }

    @Override
    public Mono<Yanki> get(Long id) {
        return repository.get(id);
    }

    @Override
    public Mono<Yanki> create(Yanki yanki) {
        return repository.create(yanki);
    }

    @Override
    public Mono<Yanki> associateDebitCard(Long id, String cardId) {
        return repository.get(id).doOnNext(y -> y.setCardId(cardId))
                .flatMap(y -> repository.update(id, y));
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.delete(id);
    }
}
