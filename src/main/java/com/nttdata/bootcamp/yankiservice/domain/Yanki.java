package com.nttdata.bootcamp.yankiservice.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class Yanki implements Serializable {
    private Long id;
    private IdType idType;
    private Long phone;
    private String email;
    private Long imei;
    private BigDecimal balance;
    private String cardId;
}
