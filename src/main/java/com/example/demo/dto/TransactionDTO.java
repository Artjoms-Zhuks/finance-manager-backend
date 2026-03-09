package com.example.demo.dto;

import lombok.Data;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Data
public class TransactionDTO {
    @NotBlank(message = "A category can't be empty")
    @Size(min = 3, max = 255, message = "A description must be startet 3 to 255 symbols")
    private String description;

    @NotNull(message = "A category can't be empty")
    private BigDecimal amount;

    @NotBlank(message = "A description can't be empty")
    private String category;
}