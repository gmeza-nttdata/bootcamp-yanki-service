package com.nttdata.bootcamp.yankiservice.infrastructure.producer;


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
    KafkaTemplate<String, String> kafkaTemplate;

    public ListenableFuture<SendResult<String, String>> sendMessage(String message) {
        log.info("Producing message " + message);
        return this.kafkaTemplate.send("TOPIC-DEMO", message);
    }


}
