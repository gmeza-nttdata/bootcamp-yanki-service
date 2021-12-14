package com.nttdata.bootcamp.yankiservice.infrastructure.producer;


import com.nttdata.bootcamp.yankiservice.infrastructure.model.dto.StatementDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;


@Slf4j
@Component
public class KafkaStringProducer {

    @Autowired
    KafkaTemplate<String, StatementDto> kafkaTemplate;

    /*
    public ListenableFuture<SendResult<String, String>> sendMessage(String message) {
        log.info("Producing message " + message);
        return this.kafkaTemplate.send("TOPIC-DEMO", message);
    }*/

    public ListenableFuture<SendResult<String, StatementDto>> makeStatement(StatementDto message) {
        log.info("Producing message " + message.toString());
        return this.kafkaTemplate.send("TOPIC-YANKI-STATEMENT", message);
    }

}
