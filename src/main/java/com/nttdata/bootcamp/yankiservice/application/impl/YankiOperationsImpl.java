package com.nttdata.bootcamp.yankiservice.application.impl;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.application.repository.YankiRepository;
import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import com.nttdata.bootcamp.yankiservice.infrastructure.model.dto.StatementDto;
import com.nttdata.bootcamp.yankiservice.infrastructure.producer.KafkaStringProducer;
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
    private final KafkaStringProducer kafkaStringProducer;

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

    @Override
    public Mono<StatementDto> send(StatementDto dto) {
        return Mono.zip(
                repository.get(dto.getSource()),
                repository.get(dto.getTarget())
        )
                .flatMap(tuple -> {
                    Yanki s = tuple.getT1(), t = tuple.getT2();

                    if (s.getBalance().subtract(dto.getAmount()).compareTo(t.getBalance()) < 0)
                        return Mono.error(new IllegalArgumentException("Source balance cannot reach transaction"));

                    // TODO: Produce event to update debit card balance
                    if (s.getCardId() != null)
                        s.getCardId();
                    // TODO: Produce event to update debit card balance
                    if (t.getCardId() != null)
                        t.getCardId();

                    s.setBalance(s.getBalance().subtract(dto.getAmount()));
                    t.setBalance(t.getBalance().add(dto.getAmount()));

                    kafkaStringProducer.makeStatement(dto);

                    return Mono.zip(
                            repository.update(s.getId(), s),
                            repository.update(t.getId(), t)
                    ).thenReturn(dto);
                });
    }
}
