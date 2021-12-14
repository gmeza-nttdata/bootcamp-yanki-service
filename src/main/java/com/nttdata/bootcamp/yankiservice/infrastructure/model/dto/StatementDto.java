package com.nttdata.bootcamp.yankiservice.infrastructure.model.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class StatementDto implements Serializer<StatementDto>, Deserializer<StatementDto> {
    private Long source;
    private Long target;
    private BigDecimal amount;


    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String arg0, StatementDto arg1) {
        byte[] retVal = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            retVal = objectMapper.writeValueAsString(arg1).getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return retVal;
    }

    @Override
    public byte[] serialize(String topic, Headers headers, StatementDto data) {
        return serialize(topic, data);
    }

    @Override
    public void close() {

    }

    @Override
    public StatementDto deserialize(String s, byte[] bytes) {
        ObjectMapper mapper = new ObjectMapper();
        StatementDto statementDto = null;
        try {
            statementDto = mapper.readValue(bytes, StatementDto.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return statementDto;
    }

    @Override
    public StatementDto deserialize(String topic, Headers headers, byte[] data) {
        ObjectMapper mapper = new ObjectMapper();
        StatementDto statementDto = null;
        try {
            statementDto = mapper.readValue(data, StatementDto.class);
        } catch (Exception e) {

            e.printStackTrace();
        }
        return statementDto;
    }


}
