package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class TransactionDTO {
    private String description;
    private BigDecimal amount;
    private String category;
}