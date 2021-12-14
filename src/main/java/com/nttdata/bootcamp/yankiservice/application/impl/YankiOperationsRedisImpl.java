package com.nttdata.bootcamp.yankiservice.application.impl;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.application.repository.YankiRepository;
import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
@RequiredArgsConstructor

public class YankiOperationsRedisImpl implements YankiOperations {
    private final ReactiveRedisOperations<String, Yanki> yankiOps;


    @Override
    public Flux<Yanki> getAll() {
        return yankiOps.keys("*")
                .flatMap(yankiOps.opsForValue()::get);
    }

    @Override
    public Mono<Yanki> get(Long id) {
        return yankiOps.opsForValue().get(id.toString())
                .doOnNext(yanki -> yankiOps.opsForValue().set(yanki.getId().toString(), yanki));
    }

    @Override
    public Mono<Yanki> create(Yanki yanki) {
        return yankiOps.opsForValue().set(yanki.getId().toString(), yanki)
                .thenReturn(yanki.getId().toString())
                .flatMap(yankiOps.opsForValue()::get);
    }

    @Override
    public Mono<Yanki> associateDebitCard(Long id, String cardId) {
        return null;
    }

    @Override
    public Mono<Void> delete(Long id) {
        return yankiOps.opsForValue().delete(id.toString()).then();
    }
}
