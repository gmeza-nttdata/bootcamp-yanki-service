package com.nttdata.bootcamp.yankiservice.infrastructure.spring.config;

import com.nttdata.bootcamp.yankiservice.application.YankiOperations;
import com.nttdata.bootcamp.yankiservice.application.impl.YankiOperationsImpl;
import com.nttdata.bootcamp.yankiservice.application.repository.YankiRepository;
import com.nttdata.bootcamp.yankiservice.infrastructure.producer.KafkaStringProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfiguration {

    @Bean
    YankiOperations operations(YankiRepository repository, KafkaStringProducer kafkaStringProducer) {
        return new YankiOperationsImpl(repository, kafkaStringProducer);
    }

}
