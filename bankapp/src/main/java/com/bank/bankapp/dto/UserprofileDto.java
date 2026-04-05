// for profile management
package com.bank.bankapp.dto;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data 
@AllArgsConstructor
public class UserprofileDto {
     
    private String name;
    private String email;
    private String accountNumber;
     private BigDecimal balance;
     

}
