package com.nttdata.bootcamp.yankiservice.infrastructure.repository;

import com.nttdata.bootcamp.yankiservice.infrastructure.model.dao.YankiDao;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface IYankiCrudRepository extends ReactiveCrudRepository<YankiDao, Long> {
}
