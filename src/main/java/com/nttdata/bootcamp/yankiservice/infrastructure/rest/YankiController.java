package com.nttdata.bootcamp.yankiservice.infrastructure.rest;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import com.nttdata.bootcamp.yankiservice.infrastructure.model.dto.OperationDto;
import com.nttdata.bootcamp.yankiservice.infrastructure.producer.KafkaStringProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

@Slf4j
@RestController
@RequestMapping("/yanki")
@RequiredArgsConstructor
public class YankiController {

    private final YankiOperations operations;

    private final KafkaStringProducer kafkaStringProducer;

    @GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Flux<Yanki>>> getAll() {
        return Mono.just(ResponseEntity.ok(operations.getAll()));
    }


    @GetMapping(value = "{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Yanki>> get(@PathVariable Long id) {
        return Mono.just(id)
                .flatMap(operations::get)
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE,
    consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Yanki>> create(@RequestBody Yanki yanki) {
        return Mono.just(yanki)
                .flatMap(operations::create)
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "associate", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Yanki>> associateDebitCard(@RequestBody OperationDto dto) {
        return operations.associateDebitCard(dto.getId(), dto.getCardId()).map(ResponseEntity::ok);
    }

    //@CacheEvict(value= "Yankis", allEntries = false, key="#id")
    @DeleteMapping(value = "{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Long id) {
        return Mono.just(id)
                .flatMap(operations::delete)
                .map(ResponseEntity::ok);
    }

/*
    @GetMapping(value = "")
    public Mono<Void> sendMessage(@RequestParam("message") String message ) {
        this.kafkaStringProducer.sendMessage(message);
        return Mono.empty();
    }
*/





}
