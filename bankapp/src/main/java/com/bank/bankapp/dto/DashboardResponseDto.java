package com.bank.bankapp.dto;
import java.math.BigDecimal;
import java.util.List;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DashboardResponseDto {
    private String name;
    private String accountNumber;
    private BigDecimal balance;
    private List<TransferResponseDto> recent;  
    
}
