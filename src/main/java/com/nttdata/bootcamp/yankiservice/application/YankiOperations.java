package com.nttdata.bootcamp.yankiservice.application;

import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import com.nttdata.bootcamp.yankiservice.infrastructure.model.dto.StatementDto;
import org.apache.kafka.common.metrics.Stat;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface YankiOperations {
    Flux<Yanki> getAll();
    Mono<Yanki> get(Long id);
    Mono<Yanki> create(Yanki yanki);
    Mono<Yanki> associateDebitCard(Long id, String cardId);
    Mono<Void> delete(Long id);
    Mono<StatementDto> send(StatementDto dto);
}
