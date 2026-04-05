package com.bank.bankapp.dto;
import java.math.BigDecimal;

import jakarta.validation.constraints.NotNull;
import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransactionRequestDto {
  // this dto is for transfer
    private String toAccountNumber;
    @NotNull(message="Amount is required")
    private BigDecimal amount;
    // private String reference;


}
