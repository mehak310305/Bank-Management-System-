package com.bank.bankapp.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.*;
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TransferResponseDto {
     
    private long id;
    private BigDecimal balance;
    private String transactiontype;
    private String fromAccount;
    private String toAccount;
    private LocalDateTime timestamp;
    private String reference;


    

}
