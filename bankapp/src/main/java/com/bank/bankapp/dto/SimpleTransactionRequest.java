package com.bank.bankapp.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SimpleTransactionRequest {
    @NotNull
    private BigDecimal amount;
    // private String reference; 
}