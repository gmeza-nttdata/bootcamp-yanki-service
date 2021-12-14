package com.nttdata.bootcamp.yankiservice.infrastructure.repository;

import com.nttdata.bootcamp.yankiservice.application.repository.YankiRepository;
import com.nttdata.bootcamp.yankiservice.domain.Yanki;
import com.nttdata.bootcamp.yankiservice.infrastructure.model.dao.YankiDao;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class YankiCrudRepository implements YankiRepository {
    @Autowired
    IYankiCrudRepository repository;


    @Override
    public Flux<Yanki> get() {
        return repository.findAll().map(this::mapFromDao);
    }

    @Override
    public Mono<Yanki> get(Long id) {
        return repository.findById(id).map(this::mapFromDao);
    }

    @Override
    public Mono<Yanki> create(Yanki yanki) {
        return Mono.just(yanki)
                .map(this::mapToDao)
                .flatMap(repository::save)
                .map(this::mapFromDao);
    }

    @Override
    public Mono<Yanki> update(Long id, Yanki yanki) {
        return Mono.just(yanki)
                .doOnNext(y -> y.setId(id))
                .map(this::mapToDao)
                .flatMap(repository::save)
                .map(this::mapFromDao);
    }

    @Override
    public Mono<Void> delete(Long id) {
        return repository.deleteById(id);
    }

    private Yanki mapFromDao(YankiDao dao) {
        Yanki yanki = new Yanki();
        BeanUtils.copyProperties(dao, yanki);
        return yanki;
    }

    private YankiDao mapToDao(Yanki yanki) {
        YankiDao dao = new YankiDao();
        BeanUtils.copyProperties(yanki, dao);
        return dao;
    }

}
