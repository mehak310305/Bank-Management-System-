package com.bank.bankapp.dto;
 import java.math.BigDecimal;

import lombok.Data;
@Data
public class UserDto {
    private Long id;
     private String name;
    private String email;
    private String password;
    private String accountNumber;
    private BigDecimal balance;
}
