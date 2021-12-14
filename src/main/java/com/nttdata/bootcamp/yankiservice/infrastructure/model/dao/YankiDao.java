package com.nttdata.bootcamp.yankiservice.infrastructure.model.dao;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;



@Data
@Document("yanki")
public class YankiDao implements Serializable {
    @Id
    private Long id;
    private String idType;
    private Long phone;
    private String email;
    private Long imei;
    private BigDecimal balance;
    private String cardId;
}
